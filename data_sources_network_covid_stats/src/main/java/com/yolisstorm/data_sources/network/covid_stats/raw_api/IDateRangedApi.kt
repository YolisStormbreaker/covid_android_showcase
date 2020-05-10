package com.yolisstorm.data_sources.network.covid_stats.raw_api

import com.yolisstorm.data_sources.network.covid_stats.BuildConfig
import com.yolisstorm.data_sources.network.covid_stats.dto.FullCase
import com.yolisstorm.data_sources.network.covid_stats.dto.ShortCase
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface IDateRangedApi {

	/**
	 * Get total data for each individual day by country with
	 * the country's location point between two dates
	 *
	 * Получить суммарные данные каждого отдельного дня по стране
	 * с локационной точкой страны между двумя датами.
	 */
	@GET("/country/{country_slug}/status/{status_type}")
	fun getByDayCasesWithCountryLocationByCountryByStatusBetweenFromTo(
		@Header(BuildConfig.GRADLE_API_HEADER_TOKEN_TITLE)
		accessToken: String = BuildConfig.GRADLE_API_TOKEN,
		@Path("country_slug")
		countrySlug: String,
		@Path("status_type")
		statusType: String,
		@Query("to")
		toDate: Date?,
		@Query("from")
		fromDate: Date?
	) : List<ShortCase>

	/**
	 * Get data grouped by day with location tags of country by
	 * country by status between two date in live mode - data may change.
	 *
	 * Получить сгруппированные по дням данные с локационнами метками страны по стране
	 * по статусу между двумя датами в живом режиме - данные могут измениться.
	 */
	@GET("/country/{country_slug}/status/{status_type}/live")
	fun getTotalByDayCasesWithCountryLocationByCountryByStatusBetweenFromToLive(
		@Header(BuildConfig.GRADLE_API_HEADER_TOKEN_TITLE)
		accessToken: String = BuildConfig.GRADLE_API_TOKEN,
		@Path("country_slug")
		countrySlug: String,
		@Path("status_type")
		statusType: String,
		@Query("to")
		toDate: Date?,
		@Query("from")
		fromDate: Date?
	) : List<ShortCase>

	/**
	 * Get complete data grouped by day by country with a country location label between two dates.
	 *
	 * Получить полные данные сгруппированне по дням по стране
	 * с локационной меткой страны между двумя датами.
	 */
	@GET("/country/{country_slug}")
	fun getByDayFullCasesWithCountryLocationByCountryBetweenFromTo(
		@Header(BuildConfig.GRADLE_API_HEADER_TOKEN_TITLE)
		accessToken: String = BuildConfig.GRADLE_API_TOKEN,
		@Path("country_slug")
		countrySlug: String,
		@Query("to")
		toDate: Date?,
		@Query("from")
		fromDate: Date?
	) : List<FullCase>

	/**
	 * Get data grouped by day for the country over a period of time
	 *
	 * Получать сгруппированные по дням данные по стране и по типу статуса за промежуток времени
	 */
	@GET("/total/country/{country_slug}/status/{status_type}")
	fun getTotalByDayCasesByCountryByStatusBetweenFromTo(
		@Header(BuildConfig.GRADLE_API_HEADER_TOKEN_TITLE)
		accessToken: String = BuildConfig.GRADLE_API_TOKEN,
		@Path("country_slug")
		countrySlug: String,
		@Path("status_type")
		statusType: String,
		@Query("to")
		toDate: Date?,
		@Query("from")
		fromDate: Date?
	) : List<ShortCase>

	/**
	 * Get data grouped by day for the country over a period of time
	 *
	 * Получать сгруппированные по дням данные по стране за промежуток времени
	 */
	@GET("/total/country/{country_slug}")
	fun getTotalByDayFullCasesByCountryBetweenFromTo(
		@Header(BuildConfig.GRADLE_API_HEADER_TOKEN_TITLE)
		accessToken: String = BuildConfig.GRADLE_API_TOKEN,
		@Path("country_slug")
		countrySlug: String,
		@Query("to")
		toDate: Date?,
		@Query("from")
		fromDate: Date?
	) : List<FullCase>


}