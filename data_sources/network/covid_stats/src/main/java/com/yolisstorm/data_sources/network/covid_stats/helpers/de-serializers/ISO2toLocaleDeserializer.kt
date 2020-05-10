package com.yolisstorm.data_sources.network.covid_stats.helpers.`de-serializers`

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.*

class ISO2toLocaleDeserializer : JsonDeserializer<Locale> {
	override fun deserialize(
		json: JsonElement?,
		typeOfT: Type?,
		context: JsonDeserializationContext?
	): Locale {
		return Locale(
			Locale.getDefault().language,
			json.toString()
		)
	}
}