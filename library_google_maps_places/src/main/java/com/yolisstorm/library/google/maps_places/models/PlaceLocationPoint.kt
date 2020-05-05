package com.yolisstorm.library.google.maps_places.models

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import com.google.maps.android.clustering.ClusterItem
import com.yolisstorm.library.extensions.second
import com.yolisstorm.library.extensions.third
import java.util.*

data class PlaceLocationPoint(
	@SerializedName("type")
	val type: String,
	@SerializedName("coordinates")
	val coordinates: List<Double>,
	@Transient
	private val pointTitle: String? = "",
	@Transient
	private val pointSnippet: String? = "",
	@Transient
	val pointUUID : UUID? = null
) : ClusterItem {
	
	
	
	override fun getSnippet(): String = pointSnippet ?: ""
	
	override fun getTitle(): String = pointTitle ?: ""
	
	override fun getPosition(): LatLng = getLatLng()
	
	fun getLatLng() =
		when (coordinates.size) {
			2 -> LatLng(coordinates.second(), coordinates.first())
			else -> LatLng(coordinates.third(), coordinates.second())
		}
	
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false
		
		other as PlaceLocationPoint
		
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