package com.yolisstorm.data_sources.databases.main

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.*


class CommonConverters {

	@TypeConverter
	fun dateToLong(inputDate: Date?) : Long = inputDate?.time ?: -1

	@TypeConverter
	fun longToDate(inputLong: Long) : Date? = if (inputLong == -1L) null else Date(inputLong)

}