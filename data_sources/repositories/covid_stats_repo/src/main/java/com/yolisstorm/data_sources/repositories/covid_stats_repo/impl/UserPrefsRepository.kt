package com.yolisstorm.data_sources.repositories.covid_stats_repo.impl

import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.yolisstorm.data_sources.repositories.covid_stats_repo.R
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.IUserPrefsRepository
import com.yolisstorm.live_shared_prefs.interfaces.ILiveSharedPrefsManager
import java.util.*

internal class UserPrefsRepository (
	private val liveSharedPrefsManager: ILiveSharedPrefsManager,
	private val androidContext : Context
) : IUserPrefsRepository {

	private fun getString(@StringRes resId: Int) = androidContext.getString(resId)

	override fun getLastUpdate(): MutableLiveData<Date> =
		liveSharedPrefsManager
			.get(
				Date::class.java,
				getString(R.string.last_update_key),
				Date()
			)

	override fun getSelectedLocale(): MutableLiveData<Locale> =
		liveSharedPrefsManager
			.get(
				Locale::class.java,
				getString(R.string.selected_locale_key),
				Locale.getDefault()
			)

	companion object {
		@Volatile
		private var instance: UserPrefsRepository? = null
		fun getInstance(
			liveSharedPrefsManager: ILiveSharedPrefsManager,
			androidContext : Context
		) = instance
			?: synchronized(this) {
				instance
					?: UserPrefsRepository(
						liveSharedPrefsManager,
						androidContext
					)
						.also { instance = it }
			}
	}

}