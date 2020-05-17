package com.yolisstorm.features.summary.views.fragments.main

import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val SummaryScreenKoinModule = module {
	single { SummaryScreenRepository.getInstance() }
	viewModel {
		SummaryScreenViewModel(
			get(),
			androidApplication()
		)
	}
}