package com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces

import com.yolisstorm.data_sources.databases.main.entities.Country
import com.yolisstorm.data_sources.repositories.covid_stats_repo.helpers.RepositoryResponse
import kotlinx.coroutines.flow.Flow

interface ICountriesRepository {

	suspend fun getListOfCountries(): Flow<RepositoryResponse<List<Country>>>
	suspend fun getCountryByISO639Code(countryCode: String): Flow<RepositoryResponse<Country>>

}