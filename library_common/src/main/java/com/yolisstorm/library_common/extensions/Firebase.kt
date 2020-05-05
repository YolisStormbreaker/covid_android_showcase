package com.yolisstorm.library_common.extensions

import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun FirebaseUser.getIdTokenAsync(forceRefresh: Boolean) =
	suspendCoroutine<String?> { continuation ->
		getIdToken(forceRefresh)
			.addOnSuccessListener { result ->
				Timber.d("Firebase token success")
				continuation.resume(result?.token)
			}.addOnFailureListener { failureResult ->
				Timber.e("Firebase token failure - ${failureResult.localizedMessage}")
				continuation.resume(null)
			}.addOnCanceledListener {
				Timber.d("Firebase token canceled")
				continuation.resume(null)
			}
	}

fun FirebaseAnalytics.setCurrentScreen(fragment: Fragment) {
	Timber.d("Current screen - simpleName - ${fragment.javaClass.simpleName} | name - ${fragment.javaClass.name}")
	setCurrentScreen(
		fragment.requireActivity(),
		fragment.javaClass.simpleName,
		fragment.javaClass.name
	)
}