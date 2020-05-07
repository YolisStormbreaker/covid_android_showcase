package com.yolisstorm.data_sources.network.covid_stats.dto

import com.google.gson.annotations.SerializedName
import java.util.*


data class Case(
	@SerializedName("Country")
	val country: String,
	@SerializedName("CountryCode")
	val countryCode: String,
	@SerializedName("Province")
	val province: String?,
	@SerializedName("City")
	val city: String?,
	@SerializedName("CityCode")
	val cityCode: String?,
	@SerializedName("Lat")
	val lat: Double,
	@SerializedName("Lon")
	val lon: Double,
	@SerializedName("Cases")
	val cases: Int,
	@SerializedName("Status")
	val status: String,
	@SerializedName("Date")
	val date: Date
)