package com.yolisstorm.library.google.maps_places.delegates.interfaces

import com.google.android.libraries.places.api.net.PlacesClient

interface GooglePlacesDelegateInterface {

	var placesClient: PlacesClient
	fun initPlacesApi()

}