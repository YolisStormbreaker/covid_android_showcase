package com.yolisstorm.data_sources.databases.main.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.yolisstorm.data_sources.databases.main.entities.Case
import com.yolisstorm.data_sources.databases.main.entities.Country


data class CountryWithCases(
	@Embedded
	val country: Country,
	@Relation(
		parentColumn = "id",
		entityColumn = "countryId"
	)
	val cases: List<Case>
)
