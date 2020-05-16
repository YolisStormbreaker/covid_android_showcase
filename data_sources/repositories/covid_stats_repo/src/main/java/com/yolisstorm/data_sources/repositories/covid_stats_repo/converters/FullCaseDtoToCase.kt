package com.yolisstorm.data_sources.repositories.covid_stats_repo.converters

import com.google.android.gms.maps.model.LatLng
import com.yolisstorm.data_sources.databases.main.entities.Case
import com.yolisstorm.data_sources.databases.main.entities.Country
import com.yolisstorm.data_sources.network.covid_stats.dto.FullCase

object FullCaseDtoToCase {

	fun FullCase.toEntity(countries: Collection<Country>) : Case? =
		countries.find {
			it.locale.language == countryCode
		}?.let {  country ->
			Case(
				location = if (lat > 0.0 && lon > 0.0) LatLng(lat, lon) else null,
				countryId = country.id,
				date = date,
				confirmed = confirmed,
				deaths = deaths,
				recovered = recovered
			)
		}

	fun FullCase.toEntityBySpecificCountry(country: Country) : Case? =
		if (countryCode != country.locale.language)
			null
		else
			Case(
				location = if (lat > 0.0 && lon > 0.0) LatLng(lat, lon) else null,
				countryId = country.id,
				date = date,
				confirmed = confirmed,
				deaths = deaths,
				recovered = recovered
			)

	fun Collection<FullCase>.toEntityBySpecificCountry(country: Country) =
		mapNotNull { fullCaseDto ->
			fullCaseDto.toEntityBySpecificCountry(country)
		}

	fun Collection<FullCase>.toEntity(countries: Collection<Country>) =
		mapNotNull { fullCaseDto ->
			fullCaseDto.toEntity(countries)
		}


}