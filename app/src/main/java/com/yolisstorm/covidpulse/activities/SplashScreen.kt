package com.yolisstorm.covidpulse.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.analytics.FirebaseAnalytics
import com.yolisstorm.covidpulse.helpers.AppConstants
import com.yolisstorm.covidpulse.helpers.FirebaseAnalyticsEvents
import com.yolisstorm.covidpulse.R
import com.yolisstorm.covidpulse.helpers.startImmediateUpdate
import kotlinx.android.synthetic.main.activity_splash_screen.*
import maes.tech.intentanim.CustomIntent
import timber.log.Timber

class SplashScreen : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash_screen)
		AppUpdateManagerFactory.create(applicationContext).run {
			appUpdateInfo.addOnSuccessListener { info ->
				when (info.updateAvailability()) {
					UpdateAvailability.UPDATE_AVAILABLE -> {
						Timber.d("Update state - UPDATE_AVAILABLE - Available VersionCode ${info.availableVersionCode()}")
						when {
							info.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) -> {
								Timber.d("Update Type IMMEDIATE is allowed")
								startImmediateUpdate(
									this,
									info,
									this@SplashScreen
								)
							}
							info.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE) -> {
								Timber.d("Update Type FLEXIBLE is allowed")
								startUpdateFlowForResult(
									info,
									AppUpdateType.FLEXIBLE,
									this@SplashScreen,
									AppConstants.REQUEST_UPDATE_FLOW
								)
							}
						}
					}
					UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS -> {
						Timber.d("Update state - DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS")
					}
					UpdateAvailability.UPDATE_NOT_AVAILABLE -> {
						Timber.d("Update state - UPDATE_NOT_AVAILABLE")
					}
					UpdateAvailability.UNKNOWN -> {
						Timber.d("Update state - UNKNOWN")
					}
				}
			}
		}

		with (motion_splash_screen) {
			transitionToEnd()
			transitionToStart()
			setTransitionListener(object : TransitionAdapter() {
				override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
					super.onTransitionCompleted(motionLayout, currentId)
					startActivity(Intent(this@SplashScreen, MainActivity::class.java))
					this@SplashScreen.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
					this@SplashScreen.finish()
				}
			})
		}
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

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		when (requestCode) {
			AppConstants.REQUEST_UPDATE_FLOW -> {
				when (requestCode) {
					Activity.RESULT_OK -> {
						Timber.d("Update result is OK")
					}
					else -> {
						Timber.d("Update result is ERROR - $resultCode")
					}
				}
			}
			ActivityResult.RESULT_IN_APP_UPDATE_FAILED -> {
				Timber.d("Update result is RESULT_IN_APP_UPDATE_FAILED - $resultCode")
			}
		}
		super.onActivityResult(requestCode, resultCode, data)
	}
}