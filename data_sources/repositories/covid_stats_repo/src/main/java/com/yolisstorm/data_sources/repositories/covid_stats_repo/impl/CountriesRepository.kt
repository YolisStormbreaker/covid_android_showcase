package com.yolisstorm.data_sources.repositories.covid_stats_repo.impl

import com.yolisstorm.data_sources.databases.main.dao.CasesDao
import com.yolisstorm.data_sources.databases.main.dao.CountriesDao
import com.yolisstorm.data_sources.network.covid_stats.interfaces.ICasesDataService
import com.yolisstorm.data_sources.network.covid_stats.interfaces.ICountryService
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.ICountriesRepository

internal class CountriesRepository (
	private val countriesDao: CountriesDao,
	private val countryService: ICountryService
) : ICountriesRepository {



	companion object {
		@Volatile
		private var instance: CountriesRepository? = null
		fun getInstance(
			countriesDao: CountriesDao,
			countryService: ICountryService
		) = instance
			?: synchronized(this) {
				instance
					?: CountriesRepository(
						countriesDao,
						countryService
					)
						.also { instance = it }
			}
	}

}