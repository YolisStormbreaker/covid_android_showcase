package com.yolisstorm.data_sources.repositories.covid_stats_repo

import com.yolisstorm.data_sources.databases.main.mainDatabaseKoinModule
import com.yolisstorm.data_sources.network.covid_stats.covidStatsNetworkKoinModule
import com.yolisstorm.data_sources.repositories.covid_stats_repo.impl.CasesRepository
import com.yolisstorm.data_sources.repositories.covid_stats_repo.impl.CountriesRepository
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.ICasesRepository
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.ICountriesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.module.Module
import org.koin.dsl.module

class CovidStatsRepositoryKoinModule {
	companion object {
		@ExperimentalCoroutinesApi
		fun getModules() : List<Module> =
			listOf(
				mainDatabaseKoinModule,
				covidStatsNetworkKoinModule,
				koinModule
			)

		private val koinModule = module {
			single<ICountriesRepository> { CountriesRepository.getInstance(get(), get()) }
			single<ICasesRepository> { CasesRepository.getInstance(get(), get(), get()) }
		}
	}
}