package com.yolisstorm.covidpulse.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.yolisstorm.covidpulse.R

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
	}

	override fun onResume() {
		super.onResume()
		FirebaseAnalytics.getInstance(applicationContext)
			.logEvent(FirebaseAnalytics.Event.APP_OPEN, null)
	}

}
