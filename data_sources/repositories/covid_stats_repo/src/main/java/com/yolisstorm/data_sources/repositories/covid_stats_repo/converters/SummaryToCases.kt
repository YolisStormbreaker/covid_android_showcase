package com.yolisstorm.data_sources.repositories.covid_stats_repo.converters

import com.yolisstorm.data_sources.databases.main.entities.Case
import com.yolisstorm.data_sources.databases.main.entities.Country
import com.yolisstorm.data_sources.network.covid_stats.dto.Summary
import com.yolisstorm.data_sources.network.covid_stats.dto.SummaryByCountry
import com.yolisstorm.library.extensions.yesterday

internal object SummaryToCases {

	/**
	 * Метод для преобразования Dto объекта Summary в понятный для БД объект
	 * В Summary нам приходит список стран с текущими данными и приростом данных
	 * относительно прошлого дня. Поэтому на выходе мы имеем пару объектов Case.
	 * Также после преобразования необходимо в каждый случай дописать правильный
	 * id-шник страны из БД.
	 */
	fun Summary.toEntity(countries: Collection<Country>) : Map<Country, Pair<Case, Case>> =
		this.countries
			.mapNotNull { summaryByCountry ->
				val country = countries.find {
					it.slug == summaryByCountry.getCountryDto().slug
				}
				if (country != null)
					country to summaryByCountry.splitIntoCases(country)
				else
					null
			}.toMap()

	fun SummaryByCountry.splitIntoCases(country: Country) : Pair<Case, Case> =
		Pair(
			Case (
				countryId = country.id,
				date = this.date,
				confirmed = totalConfirmed,
				deaths = totalDeaths,
				recovered = totalRecovered
			),
			Case (
				countryId = country.id,
				date = date.yesterday(),
				confirmed = totalConfirmed - newConfirmed,
				deaths = totalDeaths - newDeaths,
				recovered = totalRecovered - newRecovered
			)
		)

}