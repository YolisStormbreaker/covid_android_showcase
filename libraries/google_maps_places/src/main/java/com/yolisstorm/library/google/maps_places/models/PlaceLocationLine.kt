package com.yolisstorm.library.google.maps_places.models

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class PlaceLocationLine(
	@SerializedName("type")
	val type: String,
	@SerializedName("coordinates")
	val coordinates: List<List<Double>>
) {
	
	fun getLine() = Pair(
		coordinates[0].let {
			LatLng(it[1], it[0])
		},
		coordinates[1].let {
			LatLng(it[1], it[0])
		}
	)
	
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false
		
		other as PlaceLocationLine
		
		if (type != other.type) return false
		if (coordinates != other.coordinates) return false
		
		return true
	}
	
	override fun hashCode(): Int {
		var result = type.hashCode()
		result = 31 * result + coordinates.hashCode()
		return result
	}
	
	
}