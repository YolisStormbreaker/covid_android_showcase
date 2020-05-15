package com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces

import android.Manifest
import androidx.annotation.RequiresPermission
import com.yolisstorm.data_sources.databases.main.entities.Case
import com.yolisstorm.data_sources.databases.main.entities.Country
import kotlinx.coroutines.flow.Flow

interface ICasesDataRepository {

	@RequiresPermission(Manifest.permission.INTERNET)
	suspend fun getTodayCaseDataByCountry(country: Country) : Flow<Result<Case>>

}
