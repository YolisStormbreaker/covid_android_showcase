package com.yolisstorm.library.common.resultWrappers.network

import com.google.gson.annotations.SerializedName
import com.yolisstorm.library.enums.BadRequestOutputStates

data class BadRequestOutput(
	@SerializedName("error")
	val badRequestCause: BadRequestOutputStates,
	@SerializedName("error_description")
	val description: String
) : ErrorOutputInterface() {
	override fun toString(): String =
		"Cause - $badRequestCause | Description - $description"
}
