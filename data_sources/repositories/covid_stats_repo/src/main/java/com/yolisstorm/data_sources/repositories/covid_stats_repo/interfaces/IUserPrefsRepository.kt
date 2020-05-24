package com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces

import androidx.lifecycle.MutableLiveData
import java.util.*

interface IUserPrefsRepository {

	fun getLastUpdate() : MutableLiveData<Date>
	fun getSelectedLocale() : MutableLiveData<Locale>

}