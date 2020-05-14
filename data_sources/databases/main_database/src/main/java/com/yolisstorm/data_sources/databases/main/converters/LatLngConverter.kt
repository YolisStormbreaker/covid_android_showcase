package com.yolisstorm.data_sources.databases.main.converters

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class LatLngConverter {
	@TypeConverter
	fun stringToModel(json: String?): LatLng {
		val gson = Gson()
		val type: Type = object : TypeToken<LatLng?>() {}.getType()
		return gson.fromJson(json, type)
	}

	@TypeConverter
	fun modelToString(position: LatLng?): String {
		val gson = Gson()
		val type: Type = object : TypeToken<LatLng?>() {}.getType()
		return gson.toJson(position, type)
	}
}