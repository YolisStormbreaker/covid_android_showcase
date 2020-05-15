package com.yolisstorm.data_sources.network.covid_stats.interfaces

import android.Manifest
import androidx.annotation.RequiresPermission
import com.yolisstorm.data_sources.network.covid_stats.dto.CountryDto
import com.yolisstorm.data_sources.network.covid_stats.dto.FullCase
import com.yolisstorm.data_sources.network.covid_stats.dto.Summary
import com.yolisstorm.library.common.resultWrappers.network.NetworkResultWrapper
import kotlinx.coroutines.flow.Flow
import java.util.*

interface ICasesDataService {

	@RequiresPermission(Manifest.permission.INTERNET)
	suspend fun getTodaySummary() : Flow<NetworkResultWrapper<Summary>>

	@RequiresPermission(Manifest.permission.INTERNET)
	suspend fun getCasesByCountryBetweenTwoDates(
		country: CountryDto,
		dateRange: Pair<Date, Date>? = null
	) : Flow<NetworkResultWrapper<List<FullCase>>>

}