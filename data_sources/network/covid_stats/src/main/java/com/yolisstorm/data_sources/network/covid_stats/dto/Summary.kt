package com.yolisstorm.data_sources.network.covid_stats.dto


import com.google.gson.annotations.SerializedName
import java.util.*

data class Summary(
	@SerializedName("Global")
	val global: Global,
	@SerializedName("Countries")
	val countries: List<SummaryByCountry>,
	@SerializedName("Date")
	val date: Date
)