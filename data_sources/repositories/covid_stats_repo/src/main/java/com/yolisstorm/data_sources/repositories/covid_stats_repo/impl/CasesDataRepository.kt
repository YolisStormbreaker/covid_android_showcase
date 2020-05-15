package com.yolisstorm.data_sources.repositories.covid_stats_repo.impl

import com.yolisstorm.data_sources.databases.main.dao.CasesDao
import com.yolisstorm.data_sources.network.covid_stats.interfaces.ICasesDataService
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.ICasesDataRepository

internal class CasesDataRepository (
	private val casesDao: CasesDao,
	private val casesDataService: ICasesDataService
) : ICasesDataRepository {


	companion object {
		@Volatile
		private var instance: CasesDataRepository? = null
		fun getInstance(
			casesDao: CasesDao,
			casesDataService: ICasesDataService
		) = instance
			?: synchronized(this) {
				instance
					?: CasesDataRepository(
						casesDao,
						casesDataService
					)
						.also { instance = it }
			}
	}
}
