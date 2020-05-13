package com.yolisstorm.library.store.ledger_lair_database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.ext.getFullName
import java.util.*
import kotlin.reflect.KClass

internal class LedgersConverters {

	@TypeConverter
	fun dateToLong(date: Date?) : Long = date?.time ?: 0

	@TypeConverter
	fun longToDate(value : Long) : Date? = if (value == 0L) null else Date(value)

	@TypeConverter
	fun kClassToString(inputClass: KClass<out Any>) : String = inputClass.getFullName()

	@TypeConverter
	fun stringToKClass(inputString: String) : KClass<out Any> = Class.forName(inputString).kotlin

}