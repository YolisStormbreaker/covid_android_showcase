package com.yolisstorm.library.google.maps_places.delegates.interfaces

import android.Manifest
import android.location.Location
import androidx.annotation.RequiresPermission
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.OnSuccessListener

interface CurrentLocationDelegateInterface {

	val isNowRequestingLocationUpdates: LiveData<Boolean>
	fun updateIsNowRequestingLocationUpdates(n: Boolean)

	val currentLocation: LiveData<Location?>
	fun updateCurrentLocation(location: Location)

	fun prepareLocationUnits()

	fun getIsFoundFirstTime(): Boolean

	fun checkLocationPermissionGranted(activity: FragmentActivity): Boolean

	@RequiresPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
	fun startLocationUpdates(activity: FragmentActivity)

	fun stopLocationUpdates()

	@RequiresPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
	fun requestLocationPermission(
		activity: FragmentActivity,
		@StringRes fineLocationRequestTitle: Int,
		@StringRes fineLocationRequestContent: Int
	)

	@RequiresPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
	fun setLastLocationUpdateListener(
		activity: FragmentActivity,
		successListener: OnSuccessListener<Location>
	)

	fun requestLocationPermissionAndStartUpdate(
		activity: FragmentActivity,
		@StringRes fineLocationRequestTitle: Int,
		@StringRes fineLocationRequestContent: Int
	)

	val REQUEST_CHECK_SETTINGS: Int

}