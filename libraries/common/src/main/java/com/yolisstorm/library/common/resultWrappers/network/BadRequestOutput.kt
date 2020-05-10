package com.yolisstorm.library.common.resultWrappers.network

import com.google.gson.annotations.SerializedName
import com.yolisstorm.data_sources.network.covid_stats.enums.BadRequestOutputStates

data class BadRequestOutput(
	@SerializedName("error")
	val badRequestCause: BadRequestOutputStates,
	@SerializedName("error_description")
	val description: String
) : ErrorOutputInterface() {
	override fun toString(): String =
		"Cause - $badRequestCause | Description - $description"
}
