package com.yolisstorm.data_sources.repositories.covid_stats_repo.impl

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yolisstorm.data_sources.databases.main.dao.CasesDao
import com.yolisstorm.data_sources.databases.main.entities.Case
import com.yolisstorm.data_sources.databases.main.entities.Country
import com.yolisstorm.data_sources.network.covid_stats.helpers.Extensions.convertIntoResult
import com.yolisstorm.data_sources.network.covid_stats.interfaces.ICasesService
import com.yolisstorm.data_sources.repositories.covid_stats_repo.converters.SummaryToCases.toEntity
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.ICasesRepository
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.ICountriesRepository
import com.yolisstorm.data_sources.repositories.covid_stats_repo.mediators.CasesRemoteMediator
import com.yolisstorm.library.extensions.second
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.io.IOException
import kotlin.time.ExperimentalTime

internal class CasesRepository (
	private val casesDao: CasesDao,
	private val casesService: ICasesService,
	private val countriesRepository: ICountriesRepository
) : ICasesRepository {

	@RequiresPermission(Manifest.permission.INTERNET)
	override suspend fun getLastTwoCasesByCountry(country: Country): Flow<Result<Pair<Case, Case>>> =
		flow <Result<Pair<Case, Case>>> {
			val local = casesDao.getLastTwoCasesByCountry(country.id)
			if (local.size == 2) {
				emit(Result.success(local.first() to local.second()))
			} else {
				casesService.getTodaySummary().convertIntoResult(this) { value ->
					countriesRepository.getListOfCountries().collect { countries ->
						when {
							countries.isFailure -> {
								emit(Result.failure(countries.exceptionOrNull() ?: UnknownError()))
							}
							countries.isSuccess -> {
								val countriesList = countries.getOrNull()
								if (countriesList == null || countriesList.isEmpty())
									emit(Result.failure(IOException()))
								else {
									val countriesWithCases = value.toEntity(countriesList)
									casesDao.insertNewCases(countriesWithCases.flatMap { it.value.toList() })
									val casesByCountry = countriesWithCases.get(country)
									if (casesByCountry == null)
										emit(Result.failure(IOException()))
									else
										emit(Result.success(casesByCountry))
								}
							}
						}
					}
				}
			}
		}

	@ExperimentalTime
	@RequiresPermission(Manifest.permission.INTERNET)
	override suspend fun getCasesByCountry(country: Country): Flow<PagingData<Case>> =
		Pager(
			config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
			remoteMediator = CasesRemoteMediator(
				casesDao, casesService, country
			),
			pagingSourceFactory = casesDao.getCasesByCountry(countryId = country.id).asPagingSourceFactory()
		).flow


	companion object {
		@ExperimentalTime
		private const val NETWORK_PAGE_SIZE = 5
		@Volatile
		private var instance: CasesRepository? = null
		fun getInstance(
			casesDao: CasesDao,
			casesService: ICasesService,
			countriesRepository: ICountriesRepository
		) = instance
			?: synchronized(this) {
				instance
					?: CasesRepository(
						casesDao,
						casesService,
						countriesRepository
					)
						.also { instance = it }
			}
	}
}
