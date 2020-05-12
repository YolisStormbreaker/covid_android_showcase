package com.yolisstorm.library.store.models

import java.lang.Exception

sealed class StoreResponse<out T> {
	data class Fresh<out T>(val value: T) : StoreResponse<T>()
	data class Spoiled<out T>(val value: T) : StoreResponse<T>()
	object Empty : StoreResponse<Nothing>()
	object NotFound : StoreResponse<Nothing>()
	data class Error(val error: Exception) : StoreResponse<Nothing>()
	
	override fun toString(): String {
		return when (this) {
			is Fresh -> "StoreResponse - Fresh - [data=$value]"
			is Spoiled -> "StoreResponse - Spoiled - [data=$value]"
			Empty -> "StoreResponse - Empty"
			NotFound -> "StoreResponse - NotFound"
			is Error -> "StoreResponse - Error - [error=$error]"
		}
	}
}