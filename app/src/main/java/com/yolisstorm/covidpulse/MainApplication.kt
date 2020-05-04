package com.yolisstorm.covidpulse

import android.app.Application
import com.crashlytics.android.core.CrashlyticsCore
import timber.log.Timber

class MainApplication : Application() {

	override fun onCreate() {
		super.onCreate()
		configureTimber()
		configureCrashReporting()
	}

	private fun configureTimber() {
		if (BuildConfig.DEBUG)
			Timber.plant(Timber.DebugTree())
	}

	private fun configureCrashReporting() {
		CrashlyticsCore.Builder()
			.disabled(BuildConfig.DEBUG)
			.build()
	}
}