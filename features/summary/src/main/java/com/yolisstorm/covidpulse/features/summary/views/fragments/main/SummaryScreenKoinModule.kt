package com.yolisstorm.covidpulse.features.summary.views.fragments.main

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val SummaryScreenKoinModule = module {
	single { SummaryScreenRepository.getInstance(get(), get()) }
	viewModel {
		SummaryScreenViewModel(
			get(),
			get(),
			androidApplication()
		)
	}
}