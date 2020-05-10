package com.yolisstorm.library.google.maps_places.models

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class PlaceLocationPolygon(
	@SerializedName("type")
	val type: String,
	@SerializedName("coordinates")
	private val coordinates: Array<Array<Array<Double>>>
) {
	
	fun getPoints(): List<LatLng> {
		val resultArray = mutableListOf<LatLng>()
		coordinates.forEach { array1 ->
			array1.forEach { array2 ->
				resultArray.add(LatLng(array2[1], array2[0]))
			}
		}
		return resultArray
	}
	
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false
		
		other as PlaceLocationPolygon
		
		if (type != other.type) return false
		if (!coordinates.contentDeepEquals(other.coordinates)) return false
		
		return true
	}
	
	override fun hashCode(): Int {
		var result = type.hashCode()
		result = 31 * result + coordinates.contentDeepHashCode()
		return result
	}
	
	
}