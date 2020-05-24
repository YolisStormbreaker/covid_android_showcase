 package com.yolisstorm.data_sources.repositories.covid_stats_repo.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yolisstorm.data_sources.databases.main.dao.CasesDao
import com.yolisstorm.data_sources.databases.main.entities.Case
import com.yolisstorm.data_sources.databases.main.entities.Country
import com.yolisstorm.data_sources.network.covid_stats.interfaces.ICasesService
import com.yolisstorm.data_sources.repositories.covid_stats_repo.converters.SummaryToCases.toEntity
import com.yolisstorm.data_sources.repositories.covid_stats_repo.helpers.DataWays
import com.yolisstorm.data_sources.repositories.covid_stats_repo.helpers.RepositoryResponse
import com.yolisstorm.data_sources.repositories.covid_stats_repo.helpers.convertIntoResult
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.ICasesRepository
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.ICountriesRepository
import com.yolisstorm.data_sources.repositories.covid_stats_repo.mediators.CasesRemoteMediator
import com.yolisstorm.library.extensions.second
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.io.IOException
import kotlin.time.ExperimentalTime

internal class CasesRepository private constructor (
	private val casesDao: CasesDao,
	private val casesService: ICasesService,
	private val countriesRepository: ICountriesRepository
) : ICasesRepository {


	override suspend fun getLastTwoCasesByCountry(
		country: Country?,
		isUrgentUpdateNeeded: Boolean
	): Flow<RepositoryResponse<Pair<Case, Case>>> =
		flow <RepositoryResponse<Pair<Case, Case>>> {
			if (country == null) {
				emit(RepositoryResponse.Error(IllegalArgumentException()))
				return@flow
			}
			val local = casesDao.getLastTwoCasesByCountryWithinTwoLastDays(country.id).asReversed()
			val all = casesDao.getFirstTwoCasesByCountryAll(country.id)
			if (local.size == 2 && isUrgentUpdateNeeded.not()) {
				emit(RepositoryResponse.Success(local.first() to local.second(), DataWays.LocalStore))
			} else {
				casesService.getTodaySummary().convertIntoResult(this) { value ->
					countriesRepository.getListOfCountries().collect { countries ->
						when (countries) {
							is RepositoryResponse.Error -> {
								emit(RepositoryResponse.Error(countries.cause ?: UnknownError()))
							}
							is RepositoryResponse.Success -> {
								val countriesList = countries.value
								if (countriesList == null || countriesList.isEmpty())
									emit(RepositoryResponse.Error(IOException()))
								else {
									val countriesWithCases = value.toEntity(countriesList)
									casesDao.insertNewCases(countriesWithCases.flatMap { it.value.toList() })
									val casesByCountry = countriesWithCases.get(country)
									if (casesByCountry == null)
										emit(RepositoryResponse.Error(IOException()))
									else
										emit(RepositoryResponse.Success(casesByCountry, DataWays.Network))
								}
							}
						}
					}
				}
			}
		}.catch {
			Timber.e(it.cause)
			emit(RepositoryResponse.Error(it))
		}

	@ExperimentalCoroutinesApi
	override suspend fun getLastTwoCasesByCountryCode(
		countryCode: String,
		isUrgentUpdateNeeded: Boolean
	): Flow<RepositoryResponse<Pair<Case, Case>>> =
		countriesRepository
			.getCountryByISO639Code(countryCode)
			.flatMapConcat {
				getLastTwoCasesByCountry(it.getOrNull(), isUrgentUpdateNeeded)
			}.catch {
				Timber.e(it.cause)
				emit(RepositoryResponse.Error(it))
			}

	@ExperimentalTime
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
