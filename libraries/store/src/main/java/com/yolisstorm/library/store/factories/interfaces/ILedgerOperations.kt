package com.yolisstorm.library.store.factories.interfaces

import java.util.*

interface ILedgerOperations<in D> {

	suspend fun checkIfEntryWithIdExist(id: D) : Boolean
	suspend fun getLastUpdateDate(id: D) : Date
	suspend fun getCreationDate(id: D) : Date
	suspend fun updateOrCreateEntryUpToCurrentDate(id: D)
	suspend fun removeEntry(id : D)
	suspend fun burnTheLedger()

}