package com.yolisstorm.library.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager

fun getScreenWidth(context: Context?): Int {
	val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
	val dm = DisplayMetrics()
	windowManager.defaultDisplay.getMetrics(dm)
	return dm.widthPixels
}

fun dpToPx(context: Context?, value: Int): Int {
	return TypedValue.applyDimension(
		TypedValue.COMPLEX_UNIT_DIP,
		value.toFloat(),
		context?.resources?.displayMetrics
	).toInt()
}

fun dpToPx(context: Context?, value: Float): Float {
	return TypedValue.applyDimension(
		TypedValue.COMPLEX_UNIT_DIP,
		value.toFloat(),
		context?.resources?.displayMetrics
	)
}