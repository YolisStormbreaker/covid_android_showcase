package com.yolisstorm.data_sources.network.covid_stats.interfaces

import android.Manifest
import androidx.annotation.RequiresPermission
import com.yolisstorm.data_sources.network.covid_stats.dto.CountryDto
import com.yolisstorm.library.common.resultWrappers.network.NetworkResultWrapper
import kotlinx.coroutines.flow.Flow

interface ICountryService {

	@RequiresPermission(Manifest.permission.INTERNET)
	suspend fun getListOfCountries() : Flow<NetworkResultWrapper<List<CountryDto>>>

}