package com.yolisstorm.library.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Date.getFormattedDate(dateFormatType : Int = DateFormat.SHORT) =
	DateFormat.getDateInstance(dateFormatType).format(this).replace('/', '.')

fun Date.getFormattedDateTime(locale: Locale, pattern: String = "dd-MMM-yy HH:mm:ss"): String =
	SimpleDateFormat(pattern, locale).format(this)

fun Date.yesterday() : Date =
	Calendar
		.getInstance()
		.also {
			it.time = this
			it.add(Calendar.DATE, -1)
		}.time

fun Date.tomorrow() : Date =
	Calendar
		.getInstance()
		.also {
			it.time = this
			it.add(Calendar.DATE, 1)
		}.time

fun Date.resetTime() : Date =
	Calendar
		.getInstance()
		.also { calendar ->
			calendar.time = this
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		}.time

fun Date.addDays(days: Int) : Date =
	Calendar
		.getInstance()
		.also {
			it.time = this
			it.add(Calendar.DATE, days)
		}.time