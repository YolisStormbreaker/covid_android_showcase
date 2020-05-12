package com.yolisstorm.library.store.ledger_lair_database.dao

import androidx.room.Dao
import java.util.*

@Dao
interface LedgerOperationsDao<in R> {

	suspend fun checkIfEntryWithIdExist(id: R) : Boolean
	suspend fun getLastUpdateDate(id: R) : Date
	suspend fun getCreationDate(id: R) : Date
	suspend fun updateOrCreateEntryUpToCurrentDate(id: R)
	suspend fun removeEntry(id : R)
	suspend fun burnTheLedger()

}