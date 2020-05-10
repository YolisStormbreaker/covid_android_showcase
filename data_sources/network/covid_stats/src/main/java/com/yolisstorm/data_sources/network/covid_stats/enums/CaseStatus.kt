package com.yolisstorm.data_sources.network.covid_stats.enums

import com.google.gson.annotations.SerializedName

enum class CaseStatus(val status: String) {

	@SerializedName("confirmed")
	Confirmed("confirmed"),
	@SerializedName("recovered")
	Recovered("recovered"),
	@SerializedName("deaths")
	Deaths("deaths");

	companion object {
		fun getByValue(value: String) = values().find { it.status == value }
	}
}