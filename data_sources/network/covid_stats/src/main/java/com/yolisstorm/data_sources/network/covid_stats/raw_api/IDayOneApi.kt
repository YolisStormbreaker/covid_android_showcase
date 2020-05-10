package com.yolisstorm.data_sources.network.covid_stats.raw_api

import com.yolisstorm.data_sources.network.covid_stats.BuildConfig
import com.yolisstorm.data_sources.network.covid_stats.dto.FullCase
import com.yolisstorm.data_sources.network.covid_stats.dto.ShortCase
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface IDayOneApi {

	/**
	 * Get total data by country places, Ungrouped by day for the selected case type,
	 * for the selected country, starting from the first day of infection.
	 * Data have a location of the region. Live (???)
	 *
	 * Получить данные по точкам страны НЕсгруппированные по дням для выбранного типа
	 * случая, для выбранной страны, начиная с первого дня заражения. Имеют локационную метку региона.
	 * Данные прямого эфира (???)
	 */
	@GET("/dayone/country/{country_slug}/status/{status_type}/live")
	fun getShortCasesWithPlacesLocationByCountryByStatusSinceFirstInfectedDayLive(
		@Header(BuildConfig.GRADLE_API_HEADER_TOKEN_TITLE)
		accessToken: String = BuildConfig.GRADLE_API_TOKEN,
		@Path("country_slug")
		countrySlug: String,
		@Path("status_type")
		statusType: String
	): List<ShortCase>

	/**
	 * Get total data by country places, Ungrouped by day for the selected case type,
	 * for the selected country, starting from the first day of infection.
	 * Data have a location of the region.
	 *
	 * Получить данные по точкам страны НЕсгруппированные по дням для выбранного типа
	 * случая, для выбранной страны, начиная с первого дня заражения. Имеют локационную метку региона.
	 */
	@GET("/dayone/country/{country_slug}/status/{status_type}")
	fun getShortCasesWithPlacesLocationByCountryByStatusSinceFirstInfectedDay(
		@Header(BuildConfig.GRADLE_API_HEADER_TOKEN_TITLE)
		accessToken: String = BuildConfig.GRADLE_API_TOKEN,
		@Path("country_slug")
		countrySlug: String,
		@Path("status_type")
		statusType: String
	): List<ShortCase>

	/**
	 * Get complete data for each type of case by country point,
	 * grouped by day with a location label, starting from the first day of infection.	 *
	 *
	 * Получить полные данные каждого типа случая
	 * по точкам страны скруппированные по дням с локационной меткой места,
	 * начиная с первого дня инфицирования.
	 */
	@GET("/dayone/country/{country_slug}")
	fun getFullCasesWithPlacesLocationByCountrySinceFirstInfectedDay(
		@Header(BuildConfig.GRADLE_API_HEADER_TOKEN_TITLE)
		accessToken: String = BuildConfig.GRADLE_API_TOKEN,
		@Path("country_slug")
		countrySlug: String
	): List<FullCase>

	/**
	 * Returns all cases starting from the first day of infection, grouped by country by day
	 *
	 * Получить полные данные каждого типа случая по стране сгруппированные по дням,
	 * начиная с первого дня заражения.
	 */
	@GET("/total/dayone/country/{country}")
	fun getTotalFullCasesByCountryGroupedByDaySinceFirstInfectedDay(
		@Header(BuildConfig.GRADLE_API_HEADER_TOKEN_TITLE)
		accessToken: String = BuildConfig.GRADLE_API_TOKEN,
		@Path("country_slug")
		countrySlug: String
	): List<FullCase>

	/**
	 * Returns all cases starting from the first day of infection, grouped by country by day
	 *
	 * Получить полныер данные каждого типа случая по стране, по типу случая,
	 * сгруппированные по дням, начиная с первого дня заражения.
	 */
	@GET("/total/dayone/country/{country}/status/{status_type}")
	fun getTotalFullCasesByCountryGroupedByDayByStatusSinceFirstInfectedDay(
		@Header(BuildConfig.GRADLE_API_HEADER_TOKEN_TITLE)
		accessToken: String = BuildConfig.GRADLE_API_TOKEN,
		@Path("country_slug")
		countrySlug: String,
		@Path("status_type")
		statusType: String
	): List<FullCase>

}