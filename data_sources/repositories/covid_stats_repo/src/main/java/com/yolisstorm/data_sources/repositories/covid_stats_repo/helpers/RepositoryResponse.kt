package com.yolisstorm.data_sources.repositories.covid_stats_repo.helpers

import android.accounts.NetworkErrorException
import com.yolisstorm.library.common.resultWrappers.network.NetworkResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect

sealed class RepositoryResponse<out T> {

	data class Success<out T>(val value: T?, val dataFlowWay: DataWays) : RepositoryResponse<T>() {
		override fun getOrNull(): T? = value
	}

	data class Error(val cause: Throwable? = null) : RepositoryResponse<Nothing>() {
		override fun getOrNull(): Nothing? = null
	}

	abstract fun getOrNull() : T?

	override fun toString(): String {
		return when (this) {
			is Success -> "Success[dataFlowWay=$dataFlowWay - data=$value]"
			is Error -> """Error[cause=$cause]"""
		}
	}
}

suspend fun <T, D> Flow<NetworkResultWrapper<T>>.convertIntoResult(
	flowCollector: FlowCollector<RepositoryResponse<D>>,
	actionIfSuccess: suspend (value: T) -> Unit
)  {
	collect { response ->
		when (response) {
			is NetworkResultWrapper.Success -> {
				actionIfSuccess(response.value)
			}
			is NetworkResultWrapper.GenericError -> {
				flowCollector.emit(RepositoryResponse.Error(response.error?.cause ?: UnknownError()))
			}
			else -> {
				flowCollector.emit(RepositoryResponse.Error(NetworkErrorException()))
			}
		}
	}
}