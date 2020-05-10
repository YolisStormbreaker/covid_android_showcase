package com.yolisstorm.library.google.maps_places.delegates.impl

import android.content.Context
import android.content.pm.PackageManager
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.yolisstorm.library.google.maps_places.delegates.interfaces.GooglePlacesDelegateInterface
import timber.log.Timber

class GooglePlacesDelegate(
	private val context: Context
) : GooglePlacesDelegateInterface {

	override lateinit var placesClient: PlacesClient

	override fun initPlacesApi() {
		Timber.d("initPlacesApi() - InitPlaces API")
		try {
			val applicationInfo = context.packageManager?.getApplicationInfo(
				context.packageName,
				PackageManager.GET_META_DATA
			)
			val bundle = applicationInfo?.metaData
			val apiKey = bundle?.getString("com.google.android.geo.API_KEY")
			if (!apiKey.isNullOrEmpty() && !Places.isInitialized()) {
				// Initialize the SDK
				Places.initialize(context, apiKey)
				placesClient = Places.createClient(context)
			} else {
				Timber.d("initPlacesApi() - Places already initialized")
			}

		} catch (ex: java.lang.Exception) {
			//Resolve error for not existing meta-tag, inform the developer about adding his api key
			Timber.e(ex, "Error while initializing Places API")
		}
	}


}