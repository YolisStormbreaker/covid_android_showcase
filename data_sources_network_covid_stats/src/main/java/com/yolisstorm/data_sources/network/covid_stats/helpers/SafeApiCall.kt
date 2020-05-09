package com.yolisstorm.data_sources.network.covid_stats.helpers

import com.google.gson.Gson
import com.yolisstorm.data_sources.network.covid_stats.helpers.models.BadRequestOutput
import com.yolisstorm.data_sources.network.covid_stats.helpers.models.ErrorOutputInterface
import com.yolisstorm.data_sources.network.covid_stats.helpers.models.ModelStateErrorOutput
import com.yolisstorm.data_sources.network.covid_stats.helpers.models.NetworkResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

@ExperimentalCoroutinesApi
suspend fun <T> safeApiInFlowCall(
	dispatcher: CoroutineDispatcher,
	apiCall: suspend () -> T
) : Flow<NetworkResultWrapper<T>> {
	return flow<NetworkResultWrapper<T>> {
		emit(NetworkResultWrapper.Success(apiCall.invoke()))
	}.catch { throwable ->
		emit(
			when (throwable) {
				is IOException -> {
					wLogIf("Network error")
					NetworkResultWrapper.NetworkError
				}
				is SocketTimeoutException -> {
					wLogIf(throwable)
					NetworkResultWrapper.ServerError
				}
				is HttpException -> {
					val code = throwable.code()
					val errorResponse = convertErrorBody(throwable)
					wLogIf("Generic error Сode - $code error - $errorResponse")
					if (code == 401)
						NetworkResultWrapper.NotAuth(errorResponse)
					else
						NetworkResultWrapper.GenericError(code, errorResponse)
				}
				else -> {
					wLogIf("Unknown Generic error")
					NetworkResultWrapper.GenericError(null, null)
				}
			}
		)
	}.flowOn(dispatcher)
}

suspend fun <T> safeApiCall(
	dispatcher: CoroutineDispatcher,
	apiCall: suspend () -> T
): NetworkResultWrapper<T> {
	return withContext(dispatcher) {
		try {
			val resultResponse = apiCall.invoke()
			Timber.d("Fresh - $resultResponse")
			NetworkResultWrapper.Success(resultResponse)
		} catch (throwable: Throwable) {
			Timber.e("safeApiCall error $throwable")
			when (throwable) {
				is IOException -> {
					Timber.e("Network error")
					NetworkResultWrapper.NetworkError
				}
				is SocketTimeoutException -> {
					Timber.e("$throwable")
					NetworkResultWrapper.ServerError
				}
				is HttpException -> {
					val code = throwable.code()
					val errorResponse = convertErrorBody(throwable)
					Timber.e("Generic error Сode - ${code} error - ${errorResponse}")
					if (code == 401)
						NetworkResultWrapper.NotAuth(errorResponse)
					else
						NetworkResultWrapper.GenericError(code, errorResponse)
				}
				else -> {
					Timber.e("Unknown Generic error")
					NetworkResultWrapper.GenericError(null, null)
				}
			}
		}
	}
}

private fun convertErrorBody(throwable: HttpException): ErrorOutputInterface? {
	return try {
		throwable.response()?.errorBody()?.string()?.let {
			when (throwable.code()) {
				400 -> Gson().fromJson(it, BadRequestOutput::class.java)
				else -> Gson().fromJson(it, ModelStateErrorOutput::class.java)
			}
		}
	} catch (exception: Exception) {
		Timber.e("Invalid error body - $exception")
		null
	}
}