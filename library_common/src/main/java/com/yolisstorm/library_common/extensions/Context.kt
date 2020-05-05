package com.yolisstorm.library_common.extensions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.hideKeyboardFrom(view: View) {
	val imm: InputMethodManager =
		getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
	imm.hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * Indicates whether the specified app ins installed and can used as an intent. This
 * method checks the package manager for installed packages that can
 * respond to an intent with the specified app. If no suitable package is
 * found, this method returns false.
 *
 * @param context The application's environment.
 * @param appName The name of the package you want to check
 *
 * @return True if app is installed
 */
fun Context.isAppAvailable(appName: String): Boolean {
	return try {
		packageManager.getPackageInfo(appName, PackageManager.GET_ACTIVITIES)
		true
	} catch (e: PackageManager.NameNotFoundException) {
		false
	}
}