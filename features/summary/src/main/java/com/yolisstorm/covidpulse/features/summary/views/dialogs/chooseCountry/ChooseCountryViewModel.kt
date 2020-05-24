package com.yolisstorm.covidpulse.features.summary.views.dialogs.chooseCountry

import android.app.Application
import androidx.lifecycle.*
import com.yolisstorm.data_sources.databases.main.entities.Country
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.ICountriesRepository
import com.yolisstorm.data_sources.repositories.covid_stats_repo.interfaces.IUserPrefsRepository
import com.yolisstorm.library.utils.Event
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ChooseCountryViewModel (
	private val countriesRepository: ICountriesRepository,
	private val userPrefs : IUserPrefsRepository,
	application: Application
) : AndroidViewModel(application) {


	private val _wantToClickCancel = MutableLiveData<Event<Unit>>()
	val wantToClickCancel: LiveData<Event<Unit>>
		get() = _wantToClickCancel
	fun isTimeToClickCancel() {
		_wantToClickCancel.value = Event(Unit)
	}

	private val _wantToChooseMyCountry = MutableLiveData<Event<Unit>>()
	val wantToChooseMyCountry: LiveData<Event<Unit>>
		get() = _wantToChooseMyCountry
	fun isTimeToChooseMyCountry() {
		_wantToChooseMyCountry.value = Event(Unit)
	}

	val selectedLocale = userPrefs.getSelectedLocale()

	var countriesList : LiveData<List<Country>?> = MutableLiveData<List<Country>?>()

	init {
		viewModelScope.launch {
			countriesList =
				countriesRepository
					.getListOfCountries()
					.map {
						it.getOrNull()
					}
					.asLiveData()
		}
	}

}