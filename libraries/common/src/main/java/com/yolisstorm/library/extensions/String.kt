package com.yolisstorm.library.extensions

import java.util.*

fun String.capsFirstLetter(locale: Locale = Locale.getDefault()): String =
	this.substring(0, 1).toUpperCase(locale) + this.substring(1)
