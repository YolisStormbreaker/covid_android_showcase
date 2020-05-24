package com.yolisstorm.covidpulse

import android.app.Application
import com.crashlytics.android.core.CrashlyticsCore
import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.yolisstorm.covidpulse.helpers.ReleaseTimberTree
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import timber.log.Timber

class MainApplication : SplitCompatApplication() {

	override fun onCreate() {
		super.onCreate()
		configureTimber()
		configureCrashReporting()
		configureKoin()
	}

	private fun configureTimber() {
		if (BuildConfig.DEBUG)
			Timber.plant(Timber.DebugTree())
		else
			Timber.plant(ReleaseTimberTree())
	}

	private fun configureCrashReporting() {
		CrashlyticsCore.Builder()
			.disabled(BuildConfig.DEBUG)
			.build()
	}

	private fun configureKoin() {
		startKoin {
			logger(object : Logger() {
				override fun log(level: Level, msg: MESSAGE) {
					if (BuildConfig.DEBUG)
						when (level) {
							Level.DEBUG -> Timber.d(msg)
							Level.ERROR -> Timber.e(msg)
							Level.INFO -> Timber.i(msg)
							Level.NONE -> Timber.w(msg)
						}
				}
			})
			androidContext(this@MainApplication)
		}
	}
}
