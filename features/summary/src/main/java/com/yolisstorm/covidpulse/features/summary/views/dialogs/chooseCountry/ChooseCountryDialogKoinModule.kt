package com.yolisstorm.covidpulse.features.summary.views.dialogs.chooseCountry

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ChooseCountryDialogKoinModule = module {
	viewModel { ChooseCountryViewModel(get(), get(), androidApplication()) }
}