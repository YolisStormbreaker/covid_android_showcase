package com.yolisstorm.covidpulse.features.summary.views.fragments.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.yolisstorm.data_sources.databases.main.entities.Case
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.IUserPrefsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.*

class SummaryScreenViewModel(
	private val repository: SummaryScreenRepository,
	private val sharedPrefs : IUserPrefsRepository,
	application: Application
) : AndroidViewModel(application) {

	var lastTwoCases: LiveData<Pair<Case, Case>?> = MutableLiveData(null)
	var casesDeath: LiveData<Pair<Long, Double>?> = MutableLiveData(null)
	var casesCovid: LiveData<Pair<Long, Double>?> = MutableLiveData(null)
	var casesRecovered: LiveData<Pair<Long, Double>?> = MutableLiveData(null)

	private val _currentLocation = sharedPrefs.getSelectedLocale()
	val currentLocation: LiveData<Locale>
		get() = _currentLocation

	private val _lastUpdate = sharedPrefs.getLastUpdate()
	val lastUpdate : LiveData<Date>
	    get() = _lastUpdate

	fun updateCurrentLocation(newLocale: Locale) {
		Timber.d("Updating locale")
		_currentLocation.value = newLocale
	}

	@FlowPreview
	@ExperimentalCoroutinesApi
	suspend fun updateLastTwoCasesData() {
		currentLocation.value?.let { locale ->
			val lastTwoCasesFlow = repository
				.getLastTwoCasesByLocale(locale)
			casesDeath = getDeaths(lastTwoCasesFlow)
			casesCovid = getCovid(lastTwoCasesFlow)
			casesRecovered = getRecovered(lastTwoCasesFlow)
			lastTwoCases = lastTwoCasesFlow.asLiveData()
			lastTwoCasesFlow.collect()
			_lastUpdate.value = Date()
		}
	}

	private fun getDeaths(lastTwoCases: Flow<Pair<Case, Case>?>) : LiveData<Pair<Long, Double>?> =
		lastTwoCases
			.makeAction {
				Pair(
					it.second.deaths.toLong(),
					(it.second.deaths - it.first.deaths).toDouble() / it.second.deaths
				)
			}

	private fun getCovid(lastTwoCases: Flow<Pair<Case, Case>?>) : LiveData<Pair<Long, Double>?> =
		lastTwoCases
			.makeAction {
				Pair(
					it.second.confirmed.toLong(),
					(it.second.confirmed - it.first.confirmed).toDouble() / it.second.confirmed
				)
			}

	private fun getRecovered(lastTwoCases: Flow<Pair<Case, Case>?>) : LiveData<Pair<Long, Double>?> =
		lastTwoCases
			.makeAction {
				Pair(
					it.second.recovered.toLong(),
					(it.second.recovered - it.first.recovered).toDouble() / it.second.recovered
				)
			}


	private fun Flow<Pair<Case, Case>?>.makeAction(action: (Pair<Case, Case>) -> Pair<Long, Double>?): LiveData<Pair<Long, Double>?> =
		map {
			if (it != null) {
				action(it)
			} else
				null
		}.asLiveData()

}