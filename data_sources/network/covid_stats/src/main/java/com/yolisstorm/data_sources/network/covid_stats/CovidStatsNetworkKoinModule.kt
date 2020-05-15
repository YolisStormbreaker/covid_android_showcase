package com.yolisstorm.data_sources.network.covid_stats

import com.yolisstorm.data_sources.network.covid_stats.helpers.RetrofitFactory
import com.yolisstorm.data_sources.network.covid_stats.impl.CasesDataService
import com.yolisstorm.data_sources.network.covid_stats.impl.CountryService
import com.yolisstorm.data_sources.network.covid_stats.interfaces.ICasesDataService
import com.yolisstorm.data_sources.network.covid_stats.interfaces.ICountryService
import com.yolisstorm.data_sources.network.covid_stats.raw_api.ICountriesApi
import com.yolisstorm.data_sources.network.covid_stats.raw_api.IDateRangedApi
import com.yolisstorm.data_sources.network.covid_stats.raw_api.ISummaryCasesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val covidStatsNetworkKoinModule = module {
	single<ICountriesApi> {
		RetrofitFactory
			.retrofit(BuildConfig.GRADLE_API_BASE_URL)
			.create(ICountriesApi::class.java)
	}
	single<ICountryService> { CountryService.getInstance(get()) }

	single<IDateRangedApi> {
		RetrofitFactory
			.retrofit(BuildConfig.GRADLE_API_BASE_URL)
			.create(IDateRangedApi::class.java)
	}
	single<ISummaryCasesApi> {
		RetrofitFactory
			.retrofit(BuildConfig.GRADLE_API_BASE_URL)
			.create(ISummaryCasesApi::class.java)
	}
	single<ICasesDataService> { CasesDataService.getInstance(get(), get()) }

}