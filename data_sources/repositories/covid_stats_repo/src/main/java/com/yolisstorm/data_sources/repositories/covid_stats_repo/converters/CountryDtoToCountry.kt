package com.yolisstorm.data_sources.repositories.covid_stats_repo.converters

import com.yolisstorm.data_sources.databases.main.entities.Country
import com.yolisstorm.data_sources.network.covid_stats.dto.CountryDto

object CountryDtoToCountry {

	fun CountryDto.toEntity(): Country =
		Country(
			locale = locale,
			slug = slug
		)

	fun Collection<CountryDto>.toEntity() =
		map {
			it.toEntity()
		}

	fun Country.toDto(): CountryDto =
		CountryDto(
			locale.displayName,
			locale = locale,
			slug = slug
		)

	fun Collection<Country>.toDto() =
		map {
			it.toDto()
		}


}