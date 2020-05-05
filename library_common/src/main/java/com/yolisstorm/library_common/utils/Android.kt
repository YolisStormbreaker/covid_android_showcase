package com.yolisstorm.library_common.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.yolisstorm.library_common.REQUEST_UPDATE_FLOW

fun openAppGooglePlayPage(context: Context, applicationId : String) {
	try {
		ContextCompat.startActivity(
			context,
			Intent(
				Intent.ACTION_VIEW,
				Uri.parse("market://details?id=${applicationId}")
			),
			null
		)
	} catch (anfe: ActivityNotFoundException) {
		ContextCompat.startActivity(
			context,
			Intent(
				Intent.ACTION_VIEW,
				Uri.parse("https://play.google.com/store/apps/details?id=${applicationId}")
			),
			null
		)
	}
}

fun startImmediateUpdate(
	appUpdateManager: AppUpdateManager,
	appUpdateInfo: AppUpdateInfo,
	activity: Activity
) {
	appUpdateManager.startUpdateFlowForResult(
		appUpdateInfo,
		AppUpdateType.IMMEDIATE,
		activity,
		REQUEST_UPDATE_FLOW
	)
}

fun getColorWithVersion(
	@ColorRes colorId: Int,
	resources: Resources,
	context: Context
): ColorStateList {
	return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
		resources.getColorStateList(colorId, context.theme)
	} else {
		resources.getColorStateList(colorId)
	}
}
