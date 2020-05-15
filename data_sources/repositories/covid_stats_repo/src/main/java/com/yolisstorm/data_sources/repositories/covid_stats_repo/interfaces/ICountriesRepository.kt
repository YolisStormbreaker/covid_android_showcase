package com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces

import android.Manifest
import androidx.annotation.RequiresPermission
import com.yolisstorm.data_sources.databases.main.entities.Country
import kotlinx.coroutines.flow.Flow

interface ICountriesRepository {

	@RequiresPermission(Manifest.permission.INTERNET)
	suspend fun getListOfCountries() : Flow<Result<List<Country>>>
	@RequiresPermission(Manifest.permission.INTERNET)
	suspend fun getCountryByISO639Code(countryCode: String) : Flow<Result<Country>>

}