package com.yolisstorm.library.store.ledger_lair_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yolisstorm.library.store.ledger_lair_database.LedgersSecrets.DATABASE_NAME
import com.yolisstorm.library.store.ledger_lair_database.dao.LedgerOperationsDao
import com.yolisstorm.library.store.ledger_lair_database.entities.LedgerEntry
import com.yolisstorm.library.store.ledger_lair_database.entities.LedgerReferenceType


@Database(
	entities = [
		LedgerEntry::class,
		LedgerReferenceType::class
	],
	version = 1
)
@TypeConverters(
	LedgersConverters::class
)
abstract class LedgerDatabase : RoomDatabase() {

	abstract val ledgerOperationsDao: LedgerOperationsDao

	companion object {

		@Volatile
		private var INSTANCE: LedgerDatabase? = null

		fun getInstance(context: Context): LedgerDatabase {
			synchronized(this) {
				var instance = INSTANCE

				if (instance == null) {
					instance = Room
						.databaseBuilder(
							context.applicationContext,
							LedgerDatabase::class.java,
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