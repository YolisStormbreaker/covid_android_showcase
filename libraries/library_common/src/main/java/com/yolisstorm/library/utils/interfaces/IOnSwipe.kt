package com.skishift.companion.utils

import android.view.View

interface IOnSwipe {
	fun onSwipeRight()
	fun onSwipeLeft()
	fun onSwipeTop()
	fun onSwipeBottom()
	fun onSingleTouch()
}