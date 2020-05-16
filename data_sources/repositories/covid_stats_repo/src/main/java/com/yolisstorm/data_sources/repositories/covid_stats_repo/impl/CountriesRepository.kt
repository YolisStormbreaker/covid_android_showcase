package com.yolisstorm.data_sources.repositories.covid_stats_repo.impl

import android.Manifest
import android.content.res.Resources
import androidx.annotation.RequiresPermission
import com.yolisstorm.data_sources.databases.main.dao.CountriesDao
import com.yolisstorm.data_sources.databases.main.entities.Country
import com.yolisstorm.data_sources.network.covid_stats.helpers.Extensions.convertIntoResult
import com.yolisstorm.data_sources.network.covid_stats.interfaces.ICountryService
import com.yolisstorm.data_sources.repositories.covid_stats_repo.converters.CountryDtoToCountry.toEntity
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.ICountriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

internal class CountriesRepository(
	private val countriesDao: CountriesDao,
	private val countryService: ICountryService
) : ICountriesRepository {

	@RequiresPermission(Manifest.permission.INTERNET)
	override suspend fun getListOfCountries(): Flow<Result<List<Country>>> =
		flow<Result<List<Country>>> {
			val local = countriesDao.getListOfCountries()
			if (local.isNotEmpty())
				emit(Result.success(local))
			else {
				countryService.getListOfCountries().convertIntoResult(this) { value ->
					emit(
						Result.success(
							countriesDao.insertCountriesAndGetItBackInOrder(value.toEntity())
						)
					)
				}
			}
		}

	@RequiresPermission(Manifest.permission.INTERNET)
	override suspend fun getCountryByISO639Code(countryCode: String): Flow<Result<Country>> =
		flow<Result<Country>> {
			val local =
				countriesDao.getCountryByLocale(Locale(Locale.getDefault().language, countryCode))
			if (local != null)
				emit(Result.success(local))
			else {
				countryService.getListOfCountries().convertIntoResult(this) { value ->
					val country = countriesDao
						.insertCountriesAndGetOneBackByLocale(
							value.toEntity(),
							Locale(Locale.getDefault().language, countryCode)
						)
					if (country == null)
						emit(Result.failure(Resources.NotFoundException()))
					else
						emit(Result.success(country))
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