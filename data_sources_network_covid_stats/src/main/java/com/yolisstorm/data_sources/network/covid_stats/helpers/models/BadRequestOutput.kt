package com.yolisstorm.repository.network.covid_stats.helpers.models

import com.google.gson.annotations.SerializedName
import com.yolisstorm.repository.network.covid_stats.enums.BadRequestOutputStates

data class BadRequestOutput(
	@SerializedName("error")
	val badRequestCause: BadRequestOutputStates,
	@SerializedName("error_description")
	val description: String
) : ErrorOutputInterface() {
	override fun toString(): String =
		"Cause - $badRequestCause | Description - $description"
}
