package com.yolisstorm.library.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Date.getFormattedDate(dateFormatType : Int = DateFormat.SHORT) =
	DateFormat.getDateInstance(dateFormatType).format(this).replace('/', '.')

fun Date.getFormattedDateTime(locale: Locale, pattern: String = "dd-MMM-yy HH:mm:ss"): String =
	SimpleDateFormat(pattern, locale).format(this)