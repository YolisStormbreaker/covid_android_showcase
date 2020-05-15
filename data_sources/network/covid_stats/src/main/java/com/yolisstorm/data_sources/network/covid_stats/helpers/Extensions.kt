package com.yolisstorm.data_sources.network.covid_stats.helpers

import android.accounts.NetworkErrorException
import com.yolisstorm.library.common.resultWrappers.network.NetworkResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect

object Extensions {

	suspend fun <T, D> Flow<NetworkResultWrapper<T>>.convertIntoResult(
		flowCollector: FlowCollector<Result<D>>,
		actionIfSuccess: suspend (value: T) -> Unit
	) {
		collect { response ->
			when (response) {
				is NetworkResultWrapper.Success -> {
					actionIfSuccess(response.value)
				}
				is NetworkResultWrapper.GenericError -> {
					flowCollector.emit(Result.failure(response.error?.cause ?: UnknownError()))
				}
				else -> {
					flowCollector.emit(Result.failure(NetworkErrorException()))
				}
			}
		}
	}
}
