package com.yolisstorm.data_sources.network.covid_stats.impl

import android.Manifest
import androidx.annotation.RequiresPermission
import com.yolisstorm.data_sources.network.covid_stats.dto.CountryDto
import com.yolisstorm.data_sources.network.covid_stats.helpers.safeApiInFlowCall
import com.yolisstorm.data_sources.network.covid_stats.interfaces.ICountryService
import com.yolisstorm.data_sources.network.covid_stats.raw_api.ICountriesApi
import com.yolisstorm.library.common.resultWrappers.network.NetworkResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
internal class CountryService(
	private val countriesApi: ICountriesApi,
	private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ICountryService {

	@RequiresPermission(Manifest.permission.INTERNET)
	override suspend fun getListOfCountries(): Flow<NetworkResultWrapper<List<CountryDto>>> =
		safeApiInFlowCall(dispatcher) {
			countriesApi.getAvailableCountriesList()
		}

	companion object {
		@Volatile
		private var instance: ICountryService? = null
		fun getInstance(
			countriesApi: ICountriesApi,
			dispatcher: CoroutineDispatcher = Dispatchers.IO
		) =
			instance
				?: synchronized(this) {
					instance
						?: CountryService(
							countriesApi,
							dispatcher
						).also { instance = it }
				}
	}
}