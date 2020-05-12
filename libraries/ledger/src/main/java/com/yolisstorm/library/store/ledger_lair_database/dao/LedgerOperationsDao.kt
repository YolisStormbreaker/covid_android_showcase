package com.yolisstorm.library.store.ledger_lair_database.dao

import androidx.room.*
import com.yolisstorm.library.store.ledger_lair_database.entities.LedgerEntry
import com.yolisstorm.library.store.ledger_lair_database.entities.LedgerEntryWithTypeReference
import com.yolisstorm.library.store.ledger_lair_database.entities.LedgerReferenceType
import java.util.*
import kotlin.reflect.KClass

@Dao
interface LedgerOperationsDao {

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insertLedgerReferenceType(referenceType: LedgerReferenceType) : Long
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertLedgerEntry(ledgerEntry: LedgerEntry) : Long
	@Transaction
	suspend fun insertOrUpdateNewLedgerEntry(referenceType: LedgerReferenceType, ledgerEntry: LedgerEntry) {
		val referenceTypeId = insertLedgerReferenceType(referenceType)
		insertLedgerEntry(
			ledgerEntry.copy(
				referenceTypeId = referenceTypeId
			)
		)
	}

	@Transaction
	@Query("SELECT * FROM LedgerReferenceType")
	suspend fun getLedgersEntriesWithReferenceType() : List<LedgerEntryWithTypeReference>

	@Delete
	suspend fun removeEntry(ledgerEntry: LedgerEntry)

	@Query("DELETE FROM LedgerEntry")
	suspend fun burnLedgerEntries()
	@Query("DELETE FROM LedgerReferenceType")
	suspend fun burnLedgerReferenceTypes()
	@Transaction
	suspend fun burnTheLedgersAllData() {
		burnLedgerEntries()
		burnLedgerReferenceTypes()
	}

}