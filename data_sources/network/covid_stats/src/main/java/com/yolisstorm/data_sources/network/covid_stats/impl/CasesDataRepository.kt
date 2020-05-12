package com.yolisstorm.data_sources.network.covid_stats.impl

import android.Manifest
import androidx.annotation.RequiresPermission
import com.yolisstorm.data_sources.network.covid_stats.dto.Country
import com.yolisstorm.data_sources.network.covid_stats.dto.Summary
import com.yolisstorm.data_sources.network.covid_stats.helpers.safeApiInFlowCall
import com.yolisstorm.data_sources.network.covid_stats.interfaces.ICasesDataRepository
import com.yolisstorm.data_sources.network.covid_stats.raw_api.IDateRangedApi
import com.yolisstorm.data_sources.network.covid_stats.raw_api.ISummaryCasesApi
import com.yolisstorm.library.common.resultWrappers.network.NetworkResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import java.util.*

@ExperimentalCoroutinesApi
class CasesDataRepository(
	private val summaryCasesApi: ISummaryCasesApi,
	private val dateRangedApi: IDateRangedApi,
	private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ICasesDataRepository {

	@RequiresPermission(Manifest.permission.INTERNET)
	override suspend fun getTodaySummary(): Flow<NetworkResultWrapper<Summary>> =
		safeApiInFlowCall(dispatcher) {
			summaryCasesApi.getDailySummaryInfoAboutCasesInAllCounties()
		}

	@RequiresPermission(Manifest.permission.INTERNET)
	override suspend fun getCasesByCountryBetweenTwoDates(
		country: Country,
		dateRange: Pair<Date, Date>?
	) =
		safeApiInFlowCall(dispatcher) {
			dateRangedApi
				.getTotalByDayFullCasesByCountryBetweenFromTo(
					countrySlug = country.slug,
					toDate = dateRange?.first,
					fromDate = dateRange?.second
				)
		}

	companion object {
		@Volatile
		private var instance: ICasesDataRepository? = null
		fun getInstance(
			summaryCasesApi: ISummaryCasesApi,
			dateRangedApi: IDateRangedApi,
			dispatcher: CoroutineDispatcher = Dispatchers.IO
		) =
			instance
				?: synchronized(this) {
					instance
						?: CasesDataRepository(
							summaryCasesApi,
							dateRangedApi,
							dispatcher
						).also { instance = it }
				}
	}

}