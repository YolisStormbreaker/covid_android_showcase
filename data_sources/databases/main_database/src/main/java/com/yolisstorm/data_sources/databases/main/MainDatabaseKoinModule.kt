package com.yolisstorm.data_sources.databases.main

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainDatabaseKoinModule = module {
	single { MainDatabase.getInstance(androidContext()).countriesDao() }
	single { MainDatabase.getInstance(androidContext()).casesDao() }
}
