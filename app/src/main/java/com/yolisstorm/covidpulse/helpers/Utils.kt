package com.yolisstorm.covidpulse.helpers

import android.app.Activity
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.yolisstorm.covidpulse.helpers.AppConstants

fun startImmediateUpdate(
	appUpdateManager: AppUpdateManager,
	appUpdateInfo: AppUpdateInfo,
	activity: Activity
) {
	appUpdateManager.startUpdateFlowForResult(
		appUpdateInfo,
		AppUpdateType.IMMEDIATE,
		activity,
		AppConstants.REQUEST_UPDATE_FLOW
	)
}