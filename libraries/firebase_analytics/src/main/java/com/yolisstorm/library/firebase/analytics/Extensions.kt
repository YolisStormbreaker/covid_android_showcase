package com.yolisstorm.library.firebase.analytics

import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import timber.log.Timber


fun FirebaseAnalytics.setCurrentScreen(fragment: Fragment) {
	Timber.d("Current screen - simpleName - ${fragment.javaClass.simpleName} | name - ${fragment.javaClass.name}")
	setCurrentScreen(
		fragment.requireActivity(),
		fragment.javaClass.simpleName,
		fragment.javaClass.name
	)
}