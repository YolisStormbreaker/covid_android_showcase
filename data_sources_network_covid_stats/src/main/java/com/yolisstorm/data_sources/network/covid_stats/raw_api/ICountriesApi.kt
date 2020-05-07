package com.yolisstorm.data_sources.network.covid_stats.raw_api

import com.yolisstorm.data_sources.network.covid_stats.dto.Country
import retrofit2.http.GET

interface ICountriesApi {

	@GET("/countries")
	suspend fun getAvailableCountriesList() : List<Country>

}