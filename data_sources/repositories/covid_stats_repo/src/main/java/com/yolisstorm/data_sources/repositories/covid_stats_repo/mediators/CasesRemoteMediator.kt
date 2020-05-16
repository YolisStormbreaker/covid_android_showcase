package com.yolisstorm.data_sources.repositories.covid_stats_repo.mediators

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.yolisstorm.data_sources.databases.main.dao.CasesDao
import com.yolisstorm.data_sources.databases.main.entities.Case
import com.yolisstorm.data_sources.databases.main.entities.Country
import com.yolisstorm.data_sources.network.covid_stats.interfaces.ICasesService
import com.yolisstorm.data_sources.repositories.covid_stats_repo.converters.CountryDtoToCountry.toDto
import com.yolisstorm.data_sources.repositories.covid_stats_repo.converters.FullCaseDtoToCase.toEntityBySpecificCountry
import com.yolisstorm.library.common.resultWrappers.network.NetworkResultWrapper
import com.yolisstorm.library.extensions.addDays
import kotlinx.coroutines.flow.collect
import java.io.IOException
import java.io.InvalidObjectException
import java.util.*

@OptIn(ExperimentalPagingApi::class)
internal class CasesRemoteMediator (
	private val casesDao: CasesDao,
	private val casesService: ICasesService,
	private val country: Country
) : RemoteMediator<Int, Case>() {

	@RequiresPermission(Manifest.permission.INTERNET)
	override suspend fun load(loadType: LoadType, state: PagingState<Int, Case>): MediatorResult {
		val page: Date = when (loadType) {
			LoadType.REFRESH -> {
				getRemoteKeyClosestToCurrentPosition(state)
			}
			LoadType.PREPEND -> {
				getRemoteKeyForFirstItem(state)
			}
			LoadType.APPEND -> {
				getRemoteKeyForLastItem(state)
			}
		}

		lateinit var result : MediatorResult
		casesService.getCasesByCountryBetweenTwoDates(
			country.toDto(),
			page to page.addDays(state.config.pageSize)
		).collect { response ->
			result = when (response) {
				is NetworkResultWrapper.Success -> {
					val cases = response.value
					val endOfPaginationReached = cases.isEmpty()
					casesDao.clearTableIfNeedAndInsertCases(loadType, cases.toEntityBySpecificCountry(country))
					MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
				}
				is NetworkResultWrapper.NetworkError -> {
					MediatorResult.Error(IOException())
				}
				else -> {
					MediatorResult.Error(IOException())
				}
			}
		}
		return result
	}

	private fun getRemoteKeyClosestToCurrentPosition(
		state: PagingState<Int, Case>
	): Date {
		// The paging library is trying to load data after the anchor position
		// Get the item closest to the anchor position
		return state.anchorPosition?.let { position ->
			state.closestItemToPosition(position)?.date
		} ?: Date()
	}

	private fun getRemoteKeyForFirstItem(state: PagingState<Int, Case>): Date {
		// Get the first page that was retrieved, that contained items.
		// From that first page, get the first item
		val firstCaseFromFirstPageDate =
			state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.date
		if (firstCaseFromFirstPageDate == null) {
			// The LoadType is PREPEND so some data was loaded before,
			// so we should have been able to get remote keys
			// If the remoteKeys are null, then we're an invalid state and we have a bug
			throw InvalidObjectException("Remote key and the prevKey should not be null")
		}
		return firstCaseFromFirstPageDate
	}

	private fun getRemoteKeyForLastItem(state: PagingState<Int, Case>): Date {
		// Get the last page that was retrieved, that contained items.
		// From that last page, get the last item
		return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.date
			?: throw InvalidObjectException("Remote key should not be null")
	}

}
