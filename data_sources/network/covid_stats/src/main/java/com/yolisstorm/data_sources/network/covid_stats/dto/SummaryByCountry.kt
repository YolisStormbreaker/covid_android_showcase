package com.yolisstorm.data_sources.network.covid_stats.dto


import com.google.gson.annotations.SerializedName
import java.util.*

data class SummaryByCountry(
	@SerializedName("Country")
	val name: String,
	@SerializedName("CountryCode")
	val locale: Locale,
	@SerializedName("Slug")
	val slug: String,
	@SerializedName("Date")
	val date: Date,
	@SerializedName("NewConfirmed")
	val newConfirmed: Int,
	@SerializedName("NewDeaths")
	val newDeaths: Int,
	@SerializedName("NewRecovered")
	val newRecovered: Int,
	@SerializedName("TotalConfirmed")
	val totalConfirmed: Int,
	@SerializedName("TotalDeaths")
	val totalDeaths: Int,
	@SerializedName("TotalRecovered")
	val totalRecovered: Int
) {
	fun getCountryDto() =
		CountryDto(
			name, locale, slug
		)
}