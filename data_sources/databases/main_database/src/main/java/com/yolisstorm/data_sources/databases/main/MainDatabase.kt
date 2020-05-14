package com.yolisstorm.data_sources.databases.main

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

const val DATABASE_NAME = "main_database"

@Database(
	entities = [],
	version = 1
)
@TypeConverters(CommonConverters::class)
abstract class MainDatabase : RoomDatabase() {


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
						.build()
					INSTANCE = instance
				}

				return instance
			}
		}
	}
}