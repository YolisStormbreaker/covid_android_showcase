package com.yolisstorm.data_sources.network.covid_stats.raw_api

import com.yolisstorm.data_sources.network.covid_stats.BuildConfig
import com.yolisstorm.data_sources.network.covid_stats.dto.Summary
import retrofit2.http.GET
import retrofit2.http.Header

interface ISummaryCasesApi {

	/**
	 * Get complete data for each type of case by country point,
	 * grouped by day with a country's location label, starting from the first day of infection.
	 *
	 * Получить полные данные для каждого типа случая заболевания по странам,
	 * сгруппированные по дням с меткой местоположения страны, начиная с первого дня заражения.
	 */
	@GET("/summary")
	fun getDailySummaryInfoAboutCasesInAllCounties(
		@Header(BuildConfig.GRADLE_API_HEADER_TOKEN_TITLE)
		accessToken: String = BuildConfig.GRADLE_API_TOKEN
	): Summary

}