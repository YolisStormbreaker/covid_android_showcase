package com.yolisstorm.data_sources.network.covid_stats.dto


import com.google.gson.annotations.SerializedName
import java.util.*

data class SummaryByCountry(
	val country: Country,
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
)