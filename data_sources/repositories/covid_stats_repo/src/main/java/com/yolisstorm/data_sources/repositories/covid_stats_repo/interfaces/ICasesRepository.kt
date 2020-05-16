package com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.paging.PagingData
import com.yolisstorm.data_sources.databases.main.entities.Case
import com.yolisstorm.data_sources.databases.main.entities.Country
import kotlinx.coroutines.flow.Flow

interface ICasesRepository {

	@RequiresPermission(Manifest.permission.INTERNET)
	suspend fun getLastTwoCasesByCountry(country: Country): Flow<Result<Pair<Case, Case>>>

	@RequiresPermission(Manifest.permission.INTERNET)
	suspend fun getCasesByCountry(country: Country) : Flow<PagingData<Case>>

}
