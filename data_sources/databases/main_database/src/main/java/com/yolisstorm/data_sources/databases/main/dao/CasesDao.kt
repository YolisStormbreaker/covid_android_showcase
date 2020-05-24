package com.yolisstorm.data_sources.databases.main.dao

import androidx.paging.DataSource
import androidx.paging.LoadType
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.yolisstorm.data_sources.databases.main.entities.Case
import com.yolisstorm.library.extensions.yesterday
import java.util.*

@Dao
interface CasesDao {

	@Insert
	suspend fun insertNewCase(case: Case): Long

	@Insert
	suspend fun insertNewCases(cases: List<Case>)

	@Query("SELECT * FROM cases WHERE cases.country_id = :countryId ORDER BY cases.date DESC")
	fun getCasesByCountry(countryId: Long): DataSource.Factory<Int, Case>

	@Query("SELECT * FROM cases WHERE cases.country_id = :countryId AND cases.date BETWEEN  :sinceDate AND :belowDate ORDER BY cases.date DESC LIMIT 2")
	suspend fun getFirstTwoCasesByCountryBetween(countryId: Long, sinceDate: Date = Date().yesterday(), belowDate: Date = Date() ): List<Case>

	@Query("SELECT * FROM cases WHERE cases.country_id = :countryId AND cases.date >= :sinceDate ORDER BY cases.date DESC LIMIT 2")
	suspend fun getFirstTwoCasesByCountryLastTwoDays(countryId: Long, sinceDate: Date = Date().yesterday()): List<Case>

	@Query("DELETE FROM cases WHERE cases.country_id = :countryId")
	suspend fun deleteCasesFromTableByCountryId(countryId: Long)

	@Transaction
	suspend fun clearTableIfNeedAndInsertCases(loadType: LoadType, cases: List<Case>) {
		cases.filter { it.countryId > 0 }.let { actualCases ->
			if (actualCases.isNotEmpty()) {
				if (loadType == LoadType.REFRESH) {
					actualCases
						.map { it.countryId }
						.forEach { id ->
							deleteCasesFromTableByCountryId(id)
						}
				}
				insertNewCases(actualCases)
			}
		}
	}

}
