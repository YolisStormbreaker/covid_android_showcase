package com.yolisstorm.data_sources.databases.main

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yolisstorm.data_sources.databases.main.converters.CommonConverters
import com.yolisstorm.data_sources.databases.main.converters.LatLngConverter
import com.yolisstorm.data_sources.databases.main.dao.CasesDao
import com.yolisstorm.data_sources.databases.main.dao.CountriesDao
import com.yolisstorm.data_sources.databases.main.entities.Case
import com.yolisstorm.data_sources.databases.main.entities.Country

private const val DATABASE_NAME = "main_database"

@Database(
	entities = [
		Case::class,
		Country::class
	],
	version = 1
)
@TypeConverters(CommonConverters::class, LatLngConverter::class)
internal abstract class MainDatabase : RoomDatabase() {

	abstract fun countriesDao(): CountriesDao
	abstract fun casesDao(): CasesDao

	companion object {

		@Volatile
		private var INSTANCE: MainDatabase? = null

		fun getInstance(context: Context): MainDatabase {
			synchronized(this) {
				var instance = INSTANCE

				if (instance == null) {
					instance = Room
						.databaseBuilder(
							context.applicationContext,
							MainDatabase::class.java,
							DATABASE_NAME
						)
						/*.addMigrations(
							MIGRATION_1_2
						)*/
						.build()
					INSTANCE = instance
				}

				return instance
			}
		}


	}
}
