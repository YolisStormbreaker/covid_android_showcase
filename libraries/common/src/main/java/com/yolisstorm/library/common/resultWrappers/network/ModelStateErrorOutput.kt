package com.yolisstorm.library.common.resultWrappers.network

import com.google.gson.annotations.SerializedName

data class ModelStateErrorOutput(
	@SerializedName("Key")
	val key: String,
	@SerializedName("Errors")
	val errors: List<String>
) : ErrorOutputInterface() {
	override fun toString(): String =
		"key - $key | errors = ${errors.joinToString(",")}"
}