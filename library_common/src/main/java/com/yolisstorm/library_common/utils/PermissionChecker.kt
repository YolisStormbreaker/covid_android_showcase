package com.yolisstorm.library_common.utils

import android.annotation.TargetApi
import android.app.Activity
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.yolisstorm.library_common.utils.PermissionChecker.PERMISSION_REQUEST

object PermissionChecker {
	const val PERMISSION_REQUEST = 15
}

/**
 * Проверка на то, что разрешение дано
 *
 * @param activity
 * @param permission разрешение, которое нужно проверить
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun checkAccessPermissionGranted(activity: Activity, permission: String): Boolean =
	ContextCompat.checkSelfPermission(
		activity.applicationContext,
		permission
	) == PackageManager.PERMISSION_GRANTED

/**
 * Вежливый запрос доступа к разрешениям
 *
 * @param activity
 * @param permission Разрешение, которое нужно запросить
 * @param explanationTitle Заголовок диалога с объяснением причины
 * @param explanationContent Объяснение необходимости этого разрешения
 */
fun requestPermission(
	activity: Activity,
	permission: String,
	explanationTitle: String,
	explanationContent: String,
	requestCode: Int = PERMISSION_REQUEST
) {
	if (!checkAccessPermissionGranted(activity, permission)) {
		// Permission is not granted
		// Should we show an explanation?
		if (ActivityCompat.shouldShowRequestPermissionRationale(
				activity, permission
			)
		) {
			// Show an explanation to the user *asynchronously* -- don't block
			// this thread waiting for the user's response! After the user
			// sees the explanation, try again to request the permission.
			val alertBuilder = AlertDialog.Builder(activity)
			alertBuilder.setCancelable(true)
			alertBuilder.setTitle(explanationTitle)
			alertBuilder.setMessage(explanationContent)
			alertBuilder.setPositiveButton(
				android.R.string.yes
			) { _, _ ->
				request(activity, permission, requestCode)
			}.create()
			alertBuilder.show()
		} else {
			// No explanation needed, we can request the permission.
			request(activity, permission, requestCode)
		}
	}
}

/**
 * Запрос доступа к разрешению
 *
 * @param activity
 * @param permission разрешение на запрос
 */
@TargetApi(Build.VERSION_CODES.M)
private fun request(activity: Activity, permission: String, requestCode: Int) {
	activity.requestPermissions(
		arrayOf(permission),
		requestCode
	)
}


/**
 * Вежливый запрос доступа к разрешениям
 *
 * @param fragment
 * @param permission Разрешение, которое нужно запросить
 * @param explanationTitle Заголовок диалога с объяснением причины
 * @param explanationContent Объяснение необходимости этого разрешения
 */
fun requestPermission(
	fragment: Fragment,
	permission: String,
	explanationTitle: String,
	explanationContent: String,
	requestCode: Int = PERMISSION_REQUEST
) {
	if (!checkAccessPermissionGranted(fragment.requireActivity(), permission)) {
		// Permission is not granted
		// Should we show an explanation?
		if (fragment.shouldShowRequestPermissionRationale(
				permission
			)
		) {
			// Show an explanation to the user *asynchronously* -- don't block
			// this thread waiting for the user's response! After the user
			// sees the explanation, try again to request the permission.
			val alertBuilder = AlertDialog.Builder(fragment.requireContext())
			alertBuilder.setCancelable(true)
			alertBuilder.setTitle(explanationTitle)
			alertBuilder.setMessage(explanationContent)
			alertBuilder.setOnCancelListener { request(fragment, permission, requestCode) }
			alertBuilder.setOnDismissListener { request(fragment, permission, requestCode) }
			alertBuilder.setPositiveButton(
				android.R.string.yes
			) { _, _ ->
				request(fragment, permission, requestCode)
			}.create()
			alertBuilder.show()
		} else {
			// No explanation needed, we can request the permission.
			request(fragment, permission, requestCode)
		}
	}
}


/**
 * Запрос доступа к разрешению
 *
 * @param fragment
 * @param permission разрешение на запрос
 */
@TargetApi(Build.VERSION_CODES.M)
private fun request(fragment: Fragment, permission: String, requestCode: Int) {
	fragment.requestPermissions(
		arrayOf(permission),
		requestCode
	)
}