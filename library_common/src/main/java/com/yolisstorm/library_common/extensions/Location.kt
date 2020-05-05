package com.yolisstorm.library_common.extensions

import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.skishift.companion.data_sources.networking.models.PlaceLocationPolygon
import com.yolisstorm.library_common.models.AddressText
import com.yolisstorm.library_common.utils.checkIfPolygonsContainsPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*

suspend fun LatLng.getLocationAddressText(
	context: Context,
	autocompleteSessionToken: AutocompleteSessionToken,
	polygons: List<PlaceLocationPolygon>? = null,
	isNeedPolygonCheck: Boolean = false
) : AddressText? =
	withContext<AddressText?>(Dispatchers.IO) {
		try {
			Geocoder(context, Locale.getDefault())
				.getFromLocation(latitude, longitude, 1)
				.let {  addresses ->
					when {
						addresses.isEmpty() -> {
							Timber.d("getLocationAddressText() - address is empty")
							null
						}
						else -> {
							addresses.firstOrNull()?.let {
								if (
									it.locality == null || it.thoroughfare == null || it.subThoroughfare == null || polygons == null && isNeedPolygonCheck ||
									polygons != null && isNeedPolygonCheck && !checkIfPolygonsContainsPoint(
										this@getLocationAddressText,
										polygons
									)
								) {
									Timber.d("getTextLocationAddress() - address is NULL")
									null
								} else {
									Timber.d("setLocationFromGeoCoder() - address is ${it.locality}, ${it.thoroughfare}, ${it.subThoroughfare}")
									val street =
										if (it.thoroughfare.contains("Олимпийск")) "улица Олимпийская" else it.thoroughfare
									"${it.locality}, ${street}, ${it.subThoroughfare}"
									with(Places.createClient(context)) {
										getPlaceById(
											getPlacesPrediction<String>(
												autocompleteSessionToken,
												"${it.locality}, ${street}, ${it.subThoroughfare}"
											) { placesPredictions ->
												placesPredictions.map {
													it.placeId
												}
											}.first()
										)!!.addressComponents!!.getAddressText()
									}
								}
							}
						}
					}
				}
		} catch (ex: Exception) {
			Timber.e(ex, "setLocationFromGeoCoder() by location: $this")
			null
		}
	}

fun LocationResult.toStringLatLng(): String {
	return "lat/lng - (${lastLocation.latitude}, ${lastLocation.longitude})"
}

fun Location.toLatLng(): LatLng = LatLng(this.latitude, this.longitude)

fun Location.toStringLatLng(): String {
	return "lat/lng - ($latitude, $longitude)"
}

fun LatLng.toLocation() =
	Location("").also {
		it.latitude = latitude
		it.longitude = longitude
	}