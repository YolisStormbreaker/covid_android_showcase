package com.yolisstorm.covidpulse.helpers

import android.util.Log
import com.crashlytics.android.Crashlytics
import timber.log.Timber

class ReleaseTimberTree : Timber.Tree() {

	override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
		when (priority) {
			Log.ERROR -> {
				Crashlytics.logException(t)
			}
			Log.WARN -> {
				Crashlytics.log(priority, tag, message)
			}
		}
	}
}