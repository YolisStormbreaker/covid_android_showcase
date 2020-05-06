package com.yolisstorm.repository.network.covid_stats.interfaces

import android.Manifest
import androidx.annotation.RequiresPermission
import com.yolisstorm.repository.network.covid_stats.dto.Country
import com.yolisstorm.repository.network.covid_stats.helpers.models.NetworkResultWrapper
import kotlinx.coroutines.flow.Flow

interface ICountryRepository {

	@RequiresPermission(Manifest.permission.INTERNET)
	suspend fun getListOfCountries() : Flow<NetworkResultWrapper<List<Country>>>

}