package com.yolisstorm.data_sources.network.covid_stats.impl

import android.Manifest
import androidx.annotation.RequiresPermission
import com.yolisstorm.data_sources.network.covid_stats.dto.Country
import com.yolisstorm.data_sources.network.covid_stats.helpers.models.NetworkResultWrapper
import com.yolisstorm.data_sources.network.covid_stats.helpers.safeApiCall
import com.yolisstorm.data_sources.network.covid_stats.helpers.safeApiInFlowCall
import com.yolisstorm.data_sources.network.covid_stats.interfaces.ICountryRepository
import com.yolisstorm.data_sources.network.covid_stats.raw_api.ICountriesApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class CountryRepository(
	private val countriesApi: ICountriesApi,
	private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ICountryRepository {

	@RequiresPermission(Manifest.permission.INTERNET)
	override suspend fun getListOfCountries(): Flow<NetworkResultWrapper<List<Country>>> =
		safeApiInFlowCall(dispatcher) {
			countriesApi.getAvailableCountriesList()
		}

	companion object {
		@Volatile
		private var instance: ICountryRepository? = null
		fun getInstance(
			countriesApi: ICountriesApi,
			dispatcher: CoroutineDispatcher = Dispatchers.IO
		) =
			instance
				?: synchronized(this) {
					instance
						?: CountryRepository(
							countriesApi,
							dispatcher
						).also { instance = it }
				}
	}
}