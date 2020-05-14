package com.yolisstorm.data_sources.databases.main.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yolisstorm.data_sources.databases.main.entities.Case

@Dao
interface CasesDao {

	@Insert
	suspend fun insertNewCase(case: Case) : Long

	@Insert
	suspend fun insertNewCases(cases: List<Case>)

	@Query("SELECT * FROM cases WHERE cases.country_id = :countryId ORDER BY cases.date DESC")
	fun getCasesByCountry(countryId : Long) : DataSource.Factory<Int, Case>

	@Query("SELECT * FROM cases WHERE cases.country_id = :countryId ORDER BY cases.date DESC LIMIT 2")
	suspend fun getLastTwoCasesByCountry(countryId: Long) : List<Case>

}