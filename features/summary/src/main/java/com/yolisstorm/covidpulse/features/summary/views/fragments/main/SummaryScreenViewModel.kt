package com.yolisstorm.covidpulse.features.summary.views.fragments.main

import android.app.Application
import androidx.lifecycle.*
import com.yolisstorm.data_sources.databases.main.entities.Case
import com.yolisstorm.data_sources.repositories.covid_stats_repo.helpers.DataWays
import com.yolisstorm.data_sources.repositories.covid_stats_repo.helpers.RepositoryResponse
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.ICasesRepository
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.IUserPrefsRepository
import com.yolisstorm.library.utils.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class SummaryScreenViewModel(
	private val casesRepository: ICasesRepository,
	private val sharedPrefs : IUserPrefsRepository,
	application: Application
) : AndroidViewModel(application) {

	private val _lastTwoCases = MutableLiveData<Pair<Case, Case>?>()
	val lastTwoCases : LiveData<Pair<Case, Case>?>
	    get() = _lastTwoCases

	private val _casesDeath = MutableLiveData<Pair<Long, Double>?>(null)
	val casesDeath : LiveData<Pair<Long, Double>?>
	    get() = _casesDeath

	private val _casesCovid = MutableLiveData<Pair<Long, Double>?>(null)
	val casesCovid : LiveData<Pair<Long, Double>?>
	    get() = _casesCovid

	private val _casesRecovered = MutableLiveData<Pair<Long, Double>?>(null)
	val casesRecovered : LiveData<Pair<Long, Double>?>
	    get() = _casesRecovered

	private val _currentLocation = sharedPrefs.getSelectedLocale()
	val currentLocation: LiveData<Locale>
		get() = _currentLocation

	private val _lastUpdate = sharedPrefs.getLastUpdate()
	val lastUpdate : LiveData<Date>
	    get() = _lastUpdate

	private val _wantToChooserCountry = MutableLiveData<Event<Unit>>()
	val wantToChooserCountry: LiveData<Event<Unit>>
		get() = _wantToChooserCountry
	fun isTimeToChooserCountry() {
		_wantToChooserCountry.value = Event(Unit)
	}

	fun updateCurrentLocation(newLocale: Locale) {
		Timber.d("Updating locale")
		_currentLocation.value = newLocale
	}


	@FlowPreview
	@ExperimentalCoroutinesApi
	suspend fun updateLastTwoCasesData(isUrgentUpdateNeeded: Boolean) {
		casesRepository
			.getLastTwoCasesByCountryCode(currentLocation.value?.country ?: Locale.getDefault().country, isUrgentUpdateNeeded)
			.onEach {
				if (it is RepositoryResponse.Success && it.dataFlowWay == DataWays.Network)
					_lastUpdate.value = Date()
				_lastTwoCases.value = it.getOrNull()
			}.collect()
	}

	fun updateDeaths(cases: Pair<Case, Case>?) {
		_casesDeath.value =
			if (cases == null) null
			else getDeaths(cases)
	}

	fun getDeaths(cases: Pair<Case, Case>) : Pair<Long, Double>? =
		Pair(
			cases.second.deaths.toLong(),
			(cases.second.deaths - cases.first.deaths).toDouble() / cases.second.deaths
		)

	private fun getDeaths(lastTwoCases: Flow<Pair<Case, Case>?>) : LiveData<Pair<Long, Double>?> =
		lastTwoCases
			.makeAction {
				getDeaths(it)
			}

	fun updateCovid(cases: Pair<Case, Case>?) {
		_casesCovid.value =
			if (cases == null) null
			else getCovid(cases)
	}

	fun getCovid(cases: Pair<Case, Case>) : Pair<Long, Double>? =
		Pair(
			cases.second.confirmed.toLong(),
			(cases.second.confirmed - cases.first.confirmed).toDouble() / cases.second.confirmed
		)

	private fun getCovid(lastTwoCases: Flow<Pair<Case, Case>?>) : LiveData<Pair<Long, Double>?> =
		lastTwoCases
			.makeAction {
				getCovid(it)
			}

	fun updateRecovered(cases: Pair<Case, Case>?) {
		_casesRecovered.value =
			if (cases == null) null
			else getRecovered(cases)
	}

	fun getRecovered(cases: Pair<Case, Case>) : Pair<Long, Double>? =
		Pair(
			cases.second.recovered.toLong(),
			(cases.second.recovered - cases.first.recovered).toDouble() / cases.second.recovered
		)

	private fun getRecovered(lastTwoCases: Flow<Pair<Case, Case>?>) : LiveData<Pair<Long, Double>?> =
		lastTwoCases
			.makeAction {
				getRecovered(it)
			}


	private fun Flow<Pair<Case, Case>?>.makeAction(action: (Pair<Case, Case>) -> Pair<Long, Double>?): LiveData<Pair<Long, Double>?> =
		map {
			if (it != null) {
				action(it)
			} else
				null
		}.asLiveData()

}