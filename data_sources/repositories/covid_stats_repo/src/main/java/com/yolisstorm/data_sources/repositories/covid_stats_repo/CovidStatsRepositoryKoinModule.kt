package com.yolisstorm.data_sources.repositories.covid_stats_repo

import android.content.Context
import com.yolisstorm.data_sources.databases.main.mainDatabaseKoinModule
import com.yolisstorm.data_sources.network.covid_stats.covidStatsNetworkKoinModule
import com.yolisstorm.data_sources.repositories.covid_stats_repo.impl.CasesRepository
import com.yolisstorm.data_sources.repositories.covid_stats_repo.impl.CountriesRepository
import com.yolisstorm.data_sources.repositories.covid_stats_repo.impl.UserPrefsRepository
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.ICasesRepository
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.ICountriesRepository
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.IUserPrefsRepository
import com.yolisstorm.live_shared_prefs.impl.LiveSharedPrefsManager
import com.yolisstorm.live_shared_prefs.interfaces.ILiveSharedPrefsManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
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
			factory (named("ILiveSharedPrefsManager_sharedPrefs")) { androidContext().getSharedPreferences(
				androidContext().getString(R.string.user_file_key),
				Context.MODE_PRIVATE
			) }
			single <ILiveSharedPrefsManager> { LiveSharedPrefsManager(get(named("ILiveSharedPrefsManager_sharedPrefs"))) }
			single<IUserPrefsRepository> { UserPrefsRepository.getInstance(
				get(),
				androidContext()
			) }
		}
	}
}