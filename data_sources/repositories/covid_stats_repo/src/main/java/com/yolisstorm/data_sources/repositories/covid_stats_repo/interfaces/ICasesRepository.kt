package com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces

import androidx.paging.PagingData
import com.yolisstorm.data_sources.databases.main.entities.Case
import com.yolisstorm.data_sources.databases.main.entities.Country
import kotlinx.coroutines.flow.Flow

interface ICasesRepository {

	suspend fun getLastTwoCasesByCountry(country: Country?): Flow<Result<Pair<Case, Case>>>

	suspend fun getLastTwoCasesByCountryCode(countryCode: String) : Flow<Result<Pair<Case, Case>>>

	suspend fun getCasesByCountry(country: Country) : Flow<PagingData<Case>>

}
