package com.yolisstorm.covidpulse

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_splash_screen.*
import timber.log.Timber

class SplashScreen : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash_screen)
		motion_splash_screen.setTransitionListener(object : TransitionAdapter() {
			override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
				super.onTransitionCompleted(motionLayout, currentId)
				startActivity(Intent(this@SplashScreen, MainActivity::class.java))
				this@SplashScreen.finish()
			}
		})
	}

	override fun onResume() {
		super.onResume()

		FirebaseAnalytics.getInstance(applicationContext)
			.logEvent(FirebaseAnalyticsEvents.SPLASH_SCREEN_OPENED, null)

		AppUpdateManagerFactory
			.create(applicationContext)
			.run {
				appUpdateInfo
					.addOnSuccessListener { appUpdateInfo ->
						when (appUpdateInfo.updateAvailability()) {
							UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS -> {
								Timber.d("Update state - ${UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS}")
								startImmediateUpdate(
									this,
									appUpdateInfo,
									this@SplashScreen
								)
							}
							else -> {
								Timber.d("Update state - ${appUpdateInfo.updateAvailability()}")
							}
						}
					}
			}

	}
}