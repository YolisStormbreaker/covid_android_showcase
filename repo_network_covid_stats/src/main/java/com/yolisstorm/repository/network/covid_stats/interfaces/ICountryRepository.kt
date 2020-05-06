package com.yolisstorm.repository.network.covid_stats.interfaces

import com.yolisstorm.repository.network.covid_stats.dto.Country
import com.yolisstorm.repository.network.covid_stats.helpers.models.NetworkResultWrapper
import kotlinx.coroutines.flow.Flow

interface ICountryRepository {

	suspend fun getListOfCountries() : Flow<NetworkResultWrapper<List<Country>>>

}