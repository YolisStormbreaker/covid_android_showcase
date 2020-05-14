package com.yolisstorm.data_sources.databases.main.converters

import androidx.room.TypeConverter
import java.util.*


class CommonConverters {

	@TypeConverter
	fun dateToLong(inputDate: Date?): Long = inputDate?.time ?: -1

	@TypeConverter
	fun longToDate(inputLong: Long): Date? = if (inputLong == -1L) null else Date(inputLong)

	@TypeConverter
	fun countryCodeToLocale(inputCountryCode: String): Locale =
		Locale(Locale.getDefault().language, inputCountryCode)

	@TypeConverter
	fun localeToCountryCode(inputLocale: Locale): String = inputLocale.country

}
