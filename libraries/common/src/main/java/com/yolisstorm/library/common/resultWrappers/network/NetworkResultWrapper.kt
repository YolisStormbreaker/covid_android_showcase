package com.yolisstorm.library.common.resultWrappers.network

sealed class NetworkResultWrapper<out T> {
	data class Success<out T>(val value: T) : NetworkResultWrapper<T>()
	data class GenericError(val code: Int? = null, val error: ErrorOutputInterface? = null) :
		NetworkResultWrapper<Nothing>()
	
	object ServerError : NetworkResultWrapper<Nothing>()
	object NetworkError : NetworkResultWrapper<Nothing>()
	data class NotAuth(val error: ErrorOutputInterface? = null) : NetworkResultWrapper<Nothing>()
	
	override fun toString(): String {
		return when (this) {
			is Success -> "Success[data=$value]"
			is GenericError -> """GenericError[
|                                       code=$code
|                                       error=$error
|                                       ]""".trimMargin()
			ServerError -> "ServerError"
			NetworkError -> "NetworkError"
			is NotAuth -> "NotAuthorized error = $error"
		}
	}
}