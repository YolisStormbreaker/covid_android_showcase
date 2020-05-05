package com.yolisstorm.library.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboardFrom(view: View) {
	val imm: InputMethodManager =
		getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
	imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.showKeyboardFrom() {
	val imm: InputMethodManager? =
		getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
	imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}