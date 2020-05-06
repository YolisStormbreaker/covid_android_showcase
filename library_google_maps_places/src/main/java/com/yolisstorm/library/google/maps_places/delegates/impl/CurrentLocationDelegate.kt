package com.yolisstorm.library.google.maps_places.delegates.impl

import android.Manifest
import android.content.Context
import android.content.IntentSender
import android.location.Location
import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener
import com.yolisstorm.library.google.maps_places.delegates.interfaces.CurrentLocationDelegateInterface
import com.yolisstorm.library.google.maps_places.extensions.toStringLatLng
import com.yolisstorm.library.utils.checkAccessPermissionGranted
import com.yolisstorm.library.utils.requestPermission
import timber.log.Timber

class CurrentLocationDelegate(
	private val context: Context
) : CurrentLocationDelegateInterface {

	override val REQUEST_CHECK_SETTINGS: Int
		get() = 0xDC71

	private val _currentLocation = MutableLiveData<Location?>(null)
	override val currentLocation: LiveData<Location?>
		get() = _currentLocation

	override fun updateCurrentLocation(location: Location) {
		Timber.d("updateCurrentLocation() - ${location.toStringLatLng()}")
		_currentLocation.value = location
	}

	private val _isNowRequestingLocationUpdates = MutableLiveData<Boolean>()
	override val isNowRequestingLocationUpdates: LiveData<Boolean>
		get() = _isNowRequestingLocationUpdates

	override fun updateIsNowRequestingLocationUpdates(n: Boolean) {
		_isNowRequestingLocationUpdates.value = n
	}

	/**
	 * Provides access to the Fused Location Provider API.
	 */
	private var mFusedLocationClient: FusedLocationProviderClient? = null

	@RequiresPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
	override fun setLastLocationUpdateListener(
		activity: FragmentActivity,
		successListener: OnSuccessListener<Location>
	) {
		if (checkLocationPermissionGranted(activity))
			mFusedLocationClient?.lastLocation?.addOnSuccessListener(successListener)
	}

	/*
		 * Provide callbacks for location events.
		 */
	private var mLocationCallback: LocationCallback? = null

	/**
	 * FusedLocationProvider API Save request parameters
	 */
	private var mLocationRequest: LocationRequest? = null

	/**
	 * Provides access to the Location Settings API.
	 */
	private var mSettingsClient: SettingsClient? = null

	/**
	 * Stores the types of location services the client is interested in using. Used for checking
	 * settings to determine if the device has optimal location settings.
	 */
	private lateinit var mLocationSettingsRequest: LocationSettingsRequest

	/**
	 * Provides to know is current location founded first Time and is need to move map's camera on it
	 */
	private val _isFoundFirstTime = MutableLiveData<Boolean>(false)
	val isFoundFirstTime: LiveData<Boolean>
		get() = _isFoundFirstTime

	override fun getIsFoundFirstTime(): Boolean = isFoundFirstTime.value ?: true

	/**
	 * Sets up the location request. Android has two location request settings:
	 * `ACCESS_COARSE_LOCATION` and `ACCESS_FINE_LOCATION`. These settings control
	 * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
	 * the AndroidManifest.xml.
	 *
	 *
	 * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
	 * interval (5 seconds), the Fused Location Provider API returns location updates that are
	 * accurate to within a few feet.
	 *
	 *
	 * These settings are appropriate for mapping applications that show real-time location
	 * updates.
	 */
	private fun createLocationRequest() {
		mLocationRequest = LocationRequest()

		// Sets the desired interval for active location updates. This interval is
		// inexact. You may not receive updates at all if no location sources are available, or
		// you may receive them slower than requested. You may also receive updates faster than
		// requested if other applications are requesting location at a faster interval.
		mLocationRequest?.interval = UPDATE_INTERVAL_IN_MINUTE

		// Sets the fastest rate for active location updates. This interval is exact, and your
		// application will never receive updates faster than this value.
		mLocationRequest?.fastestInterval =
			FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS

		mLocationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
	}

	/**
	 * Uses a [com.google.android.gms.location.LocationSettingsRequest.Builder] to build
	 * a [com.google.android.gms.location.LocationSettingsRequest] that is used for checking
	 * if a device has the needed location settings.
	 */
	private fun buildLocationSettingsRequest() {
		mLocationRequest?.let {
			val builder = LocationSettingsRequest.Builder()
			builder.addLocationRequest(it)
			mLocationSettingsRequest = builder.build()
		}
	}

	/**
	 * Creates a callback for receiving location events.
	 */
	private fun createLocationCallback() {
		mLocationCallback = object : LocationCallback() {
			override fun onLocationResult(locationResult: LocationResult?) {
				super.onLocationResult(locationResult)
				Timber.d("LocationCallback() - new location fetched - ${locationResult?.toStringLatLng() ?: "null"}")
				locationResult?.let {
					_isFoundFirstTime.value = currentLocation.value == null
					updateCurrentLocation(locationResult.lastLocation)
				}
			}
		}
	}

	override fun prepareLocationUnits() {


		mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
		mSettingsClient = LocationServices.getSettingsClient(context)

		// Kick off the process of building the LocationCallback, LocationRequest, and
		// LocationSettingsRequest objects.
		createLocationCallback()
		createLocationRequest()
		buildLocationSettingsRequest()

	}

	@RequiresPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
	override fun startLocationUpdates(
		activity: FragmentActivity
	) {
		Timber.d("startLocationUpdates() - Starting location update")
		mSettingsClient?.checkLocationSettings(mLocationSettingsRequest)
			?.addOnSuccessListener {
				Timber.d("startLocationUpdates() -Network- All location settings are satisfied.")

				if (checkLocationPermissionGranted(activity))
					mFusedLocationClient?.requestLocationUpdates(
						mLocationRequest,
						mLocationCallback,
						Looper.myLooper()
					)
				_isNowRequestingLocationUpdates.value = true
			}
			?.addOnFailureListener { ex ->
				ex as ApiException
				when (ex.statusCode) {
					LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
						Timber.d("startLocationUpdates() - Failure - Location settings are not satisfied. Attempting to upgrade location settings")
						try {
							// Show the dialog by calling startResolutionForResult(), and check the
							// result in onActivityResult().
							val rae = ex as ResolvableApiException
							rae.startResolutionForResult(
								activity,
								REQUEST_CHECK_SETTINGS
							)
						} catch (seiEx: IntentSender.SendIntentException) {
							Timber.d("PendingIntent unable to execute request.")
						}
					}
					LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
						Timber.d("startLocationUpdates() - Failure - Location settings are inadequate, and cannot be fixed here. Fix in Settings.")
						_isNowRequestingLocationUpdates.value = false
					}
				}
			}
	}

	override fun stopLocationUpdates() {
		if (isNowRequestingLocationUpdates.value?.not() == true) {
			Timber.d("stopLocationUpdates() - location updates never requested")
			return
		}
		// It is a good practice to remove location requests when the activity is in a paused or
		// stopped state. Doing so helps battery performance and is especially
		// recommended in applications that request frequent location updates.
		Timber.d("stopLocationUpdates() - removing location requests")
		mFusedLocationClient?.removeLocationUpdates(mLocationCallback)
			?.addOnSuccessListener {
				Timber.d("stopLocationUpdates() - isNowRequestingLocationUpdates = false")
				_isNowRequestingLocationUpdates.value = false
			}
	}

	override fun checkLocationPermissionGranted(activity: FragmentActivity): Boolean {
		return checkAccessPermissionGranted(
			activity,
			Manifest.permission.ACCESS_FINE_LOCATION
		)
	}

	@RequiresPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
	override fun requestLocationPermissionAndStartUpdate(
		activity: FragmentActivity,
		@StringRes fineLocationRequestTitle: Int,
		@StringRes fineLocationRequestContent: Int
	) {
		if (checkLocationPermissionGranted(activity).not())
			requestLocationPermission(
				activity,
				fineLocationRequestTitle,
				fineLocationRequestContent
			)
		startLocationUpdates(activity)
	}


	override fun requestLocationPermission(
		activity: FragmentActivity,
		@StringRes fineLocationRequestTitle: Int,
		@StringRes fineLocationRequestContent: Int
	) {
		requestPermission(
			activity,
			Manifest.permission.ACCESS_FINE_LOCATION,
			context.resources.getString(fineLocationRequestTitle),
			context.resources.getString(fineLocationRequestContent)
		)
	}

	companion object {
		/**
		 * Constant used in the location settings dialog.
		 */
		const val REQUEST_CHECK_SETTINGS = 0x1

		/**
		 * The desired interval for location updates. Inexact. Updates may be more or less frequent.
		 */
		private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 60000   //1 min
		private const val UPDATE_INTERVAL_IN_MINUTE: Long = 5 * 60 * 1000     //5 mins

		/**
		 * The fastest rate for active location updates. Exact. Updates will never be more frequent
		 * than this value.
		 */
		private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
			UPDATE_INTERVAL_IN_MILLISECONDS / 30
	}
}