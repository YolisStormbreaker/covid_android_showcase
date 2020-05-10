package com.yolisstorm.library.google.maps_places.extensions

import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.*
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.yolisstorm.library.google.maps_places.models.AddressText
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


suspend fun <T : Any> PlacesClient.getPlacesPrediction(
	token: AutocompleteSessionToken,
	query: String,
	biasBoundsLatLng: Pair<LatLng, LatLng>? = null,
	restrictBoundsLatLng: RectangularBounds? = null,
	typeFilter: TypeFilter = TypeFilter.ADDRESS,
	language: String? = "RU",
	dataAction : (listOfPredictions : List<AutocompletePrediction>) -> List<T>
) : List<T> {
	Timber.d("current lngName = $language")
	return suspendCoroutine { continuation ->
		with(
			FindAutocompletePredictionsRequest
				.builder()
				.apply {
					setSessionToken(token)
					setQuery(query)
					setCountry(language)
					if (biasBoundsLatLng != null)
						setLocationBias(
							RectangularBounds.newInstance(
								biasBoundsLatLng.first,
								biasBoundsLatLng.second
							)
						)
					restrictBoundsLatLng?.let { bounds ->
						setLocationRestriction(bounds)
					}
					setTypeFilter(typeFilter)
				}
				.build())
		{
			findAutocompletePredictions(this)
				.addOnSuccessListener { response ->
					Timber.d("Prediction completed.")
					continuation.resume(
						dataAction(response.autocompletePredictions)
					)
				}
				.addOnFailureListener { ex ->
					if (ex is ApiException)
						Timber.e(ex, "Error while prediction the place - query = $query")
					continuation.resume(listOf())
				}
		}
	}
}

suspend fun PlacesClient.getPlaceById(
	placeId: String,
	placeFields: ArrayList<Place.Field> = arrayListOf<Place.Field>(
		Place.Field.ADDRESS,
		Place.Field.ADDRESS_COMPONENTS,
		Place.Field.NAME,
		Place.Field.LAT_LNG
	)
): Place? {

	val request = FetchPlaceRequest.newInstance(placeId, placeFields)

	return suspendCoroutine { continuation ->
		fetchPlace(request)
			.addOnSuccessListener { response ->
				Timber.d("Place found: ${response.place.name}")
				continuation.resume(response.place)
			}
			.addOnFailureListener { exception ->
				if (exception is ApiException) {
					Timber.e("Place not found: code - ${exception.statusCode} | msg - ${exception.localizedMessage}")
				}
				continuation.resume(null)
			}
			.addOnCanceledListener {
				Timber.d("Place searching canceled by placeId = $placeId")
				continuation.resume(null)
			}
	}

}

//https://developers.google.com/maps/documentation/geocoding/intro#Types
fun AddressComponents.getAddressText() : AddressText =
	with(this.asList()) {
		AddressText (
			country = this.find { it.types.contains("country") }?.name,
			city = this.find { it.types.contains("locality") }?.name,
			street = this.find { it.types.contains("street_address") }?.name ?: this.find { it.types.contains("route") }?.name,
			house = this.find { it.types.contains("street_number") }?.name,
			flat = null,
			zipCode = this.find { it.types.contains("postal_code")}?.name
		)
	}