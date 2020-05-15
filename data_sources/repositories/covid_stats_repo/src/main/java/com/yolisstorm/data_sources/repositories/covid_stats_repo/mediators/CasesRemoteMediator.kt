package com.yolisstorm.data_sources.repositories.covid_stats_repo.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.yolisstorm.data_sources.databases.main.entities.Case
import java.util.*

@OptIn(ExperimentalPagingApi::class)
class CasesRemoteMediator : RemoteMediator<Date, Case>() {

	override suspend fun load(loadType: LoadType, state: PagingState<Date, Case>): MediatorResult {
		throw NotImplementedError()
	}


}