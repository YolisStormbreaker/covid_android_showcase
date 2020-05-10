package com.yolisstorm.data_sources.network.covid_stats.raw_api

import com.yolisstorm.data_sources.network.covid_stats.BuildConfig
import com.yolisstorm.data_sources.network.covid_stats.dto.Country
import retrofit2.http.GET
import retrofit2.http.Header

interface ICountriesApi {

	/**
	 * Returns all the available countries and provinces,
	 * as well as the country slug for per country requests.
	 */
	@GET("/countries")
	suspend fun getAvailableCountriesList(
		@Header(BuildConfig.GRADLE_API_HEADER_TOKEN_TITLE)
		accessToken: String = BuildConfig.GRADLE_API_TOKEN
	) : List<Country>

}