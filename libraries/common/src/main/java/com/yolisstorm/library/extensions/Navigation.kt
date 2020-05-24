package com.yolisstorm.library.extensions

import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.safeNavigateTo(currentFragmentYouThink : Int, resId : NavDirections) {
	if (currentDestination?.id == currentFragmentYouThink) {
		navigate(resId)
	}
}