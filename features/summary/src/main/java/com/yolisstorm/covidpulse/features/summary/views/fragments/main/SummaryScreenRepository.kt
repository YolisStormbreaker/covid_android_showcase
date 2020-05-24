package com.yolisstorm.covidpulse.features.summary.views.fragments.main

import com.yolisstorm.data_sources.databases.main.entities.Case
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.ICasesRepository
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.ICountriesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import java.util.*

class SummaryScreenRepository private constructor(
	private val casesRepository: ICasesRepository,
	private val countriesRepository: ICountriesRepository
) {



	@FlowPreview
	@ExperimentalCoroutinesApi
	suspend fun getLastTwoCasesByLocale(locale: Locale): Flow<Pair<Case, Case>?> =
		casesRepository
			.getLastTwoCasesByCountryCode(locale.country, false)
			.map { it.getOrNull() }




	companion object {
		//Для Singleton
		@Volatile
		private var instance: SummaryScreenRepository? = null

		fun getInstance(
			casesRepository: ICasesRepository,
			countriesRepository: ICountriesRepository
		) =
			instance
				?: synchronized(this) {
					instance
						?: SummaryScreenRepository(
							casesRepository,
							countriesRepository
						)
							.also { instance = it }
				}
	}

}