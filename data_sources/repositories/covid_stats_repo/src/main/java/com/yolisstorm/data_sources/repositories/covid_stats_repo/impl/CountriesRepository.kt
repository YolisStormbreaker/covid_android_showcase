package com.yolisstorm.data_sources.repositories.covid_stats_repo.impl

import android.content.res.Resources
import com.yolisstorm.data_sources.databases.main.converters.CommonConverters
import com.yolisstorm.data_sources.databases.main.dao.CountriesDao
import com.yolisstorm.data_sources.databases.main.entities.Country
import com.yolisstorm.data_sources.network.covid_stats.interfaces.ICountryService
import com.yolisstorm.data_sources.repositories.covid_stats_repo.converters.CountryDtoToCountry.toEntity
import com.yolisstorm.data_sources.repositories.covid_stats_repo.helpers.DataWays
import com.yolisstorm.data_sources.repositories.covid_stats_repo.helpers.RepositoryResponse
import com.yolisstorm.data_sources.repositories.covid_stats_repo.helpers.convertIntoResult
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.ICountriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class CountriesRepository private constructor(
	private val countriesDao: CountriesDao,
	private val countryService: ICountryService
) : ICountriesRepository {

	override suspend fun getListOfCountries(): Flow<RepositoryResponse<List<Country>>> =
		flow<RepositoryResponse<List<Country>>> {
			val local = countriesDao.getListOfCountries()
			if (local.isNotEmpty())
				emit(RepositoryResponse.Success(local, DataWays.LocalStore))
			else {
				countryService.getListOfCountries().convertIntoResult(this) { value ->
					emit(
						RepositoryResponse.Success(
							countriesDao.insertCountriesAndGetItBackInOrder(value.toEntity()),
							DataWays.Network
						)
					)
				}
			}
		}

	override suspend fun getCountryByISO639Code(countryCode: String): Flow<RepositoryResponse<Country>> =
		flow<RepositoryResponse<Country>> {
			val local =
				countriesDao.getCountryByLocale(CommonConverters().countryCodeToLocale(countryCode))
			if (local != null)
				emit(RepositoryResponse.Success(local, DataWays.LocalStore))
			else {
				countryService.getListOfCountries().convertIntoResult(this) { value ->
					val country = countriesDao
						.insertCountriesAndGetOneBackByLocale(
							value.toEntity(),
							CommonConverters().countryCodeToLocale(countryCode)
						)
					val countries = countriesDao.getListOfCountries()
					if (country == null)
						emit(RepositoryResponse.Error(Resources.NotFoundException()))
					else
						emit(RepositoryResponse.Success(country, DataWays.Network))
				}
			}
		}

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