package com.yolisstorm.data_sources.databases.main.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yolisstorm.data_sources.databases.main.entities.Country

@Dao
interface CountriesDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertCountriesList(countries: List<Country>)

	@Query("SELECT * FROM countries ORDER BY country_slug ASC")
	suspend fun getListOfCountries(): List<Country>

	@Query("SELECT * FROM countries WHERE country_slug = :countrySlug ")
	suspend fun getCountryBySlug(countrySlug: String): Country?

	@Query("SELECT country_slug FROM countries WHERE id = :countryId")
	suspend fun getCountrySlugById(countryId: Long): String?

}
