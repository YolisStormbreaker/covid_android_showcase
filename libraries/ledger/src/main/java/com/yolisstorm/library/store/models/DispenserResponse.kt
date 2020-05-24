package com.yolisstorm.library.store.models

import java.lang.Exception

sealed class DispenserResponse<out T> {
	data class Fresh<out T>(val value: T) : DispenserResponse<T>()
	data class Spoiled<out T>(val value: T) : DispenserResponse<T>()
	object Empty : DispenserResponse<Nothing>()
	object NotFound : DispenserResponse<Nothing>()
	data class Error(val error: Exception) : DispenserResponse<Nothing>()
	
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