package com.yolisstorm.library_common.extensions

import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.safeNavigateTo(currentFragmentYouThink : Int, resId : NavDirections) {
	if (currentDestination?.id == currentFragmentYouThink) {
		navigate(resId)
	}
}