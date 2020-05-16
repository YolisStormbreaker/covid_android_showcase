package com.yolisstorm.data_sources.databases.main.dao

import androidx.room.*
import com.yolisstorm.data_sources.databases.main.entities.Country
import java.util.*

@Dao
interface CountriesDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertCountriesList(countries: List<Country>)

	@Query("SELECT * FROM countries ORDER BY country_slug ASC")
	suspend fun getListOfCountries(): List<Country>

	@Transaction
	suspend fun insertCountriesAndGetItBackInOrder(countries: List<Country>) : List<Country> {
		insertCountriesList(countries)
		return getListOfCountries()
	}

	@Transaction
	suspend fun insertCountriesAndGetOneBackByLocale(countries: List<Country>, needToGet: Locale) : Country? {
		insertCountriesList(countries)
		return getCountryByLocale(needToGet)
	}

	@Query("SELECT * FROM countries WHERE country_code = :countryCode")
	suspend fun getCountryByLocale(countryCode: Locale) : Country?

	@Query("SELECT * FROM countries WHERE country_slug = :countrySlug ")
	suspend fun getCountryBySlug(countrySlug: String): Country?

	@Query("SELECT country_slug FROM countries WHERE id = :countryId")
	suspend fun getCountrySlugById(countryId: Long): String?

}
