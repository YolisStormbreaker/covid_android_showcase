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
	@Update
	suspend fun updateLedgerEntry(ledgerEntry: LedgerEntry)

	@Transaction
	@Query("SELECT * FROM LedgerReferenceType WHERE LedgerReferenceType.referenceId = :referenceTypeId")
	suspend fun getLedgersEntriesWithReferenceType(referenceTypeId : Long) : LedgerEntryWithTypeReference
	@Query("SELECT * FROM LedgerEntry WHERE LedgerEntry.objectIdInRefTable = :objectTableId")
	suspend fun getLedgerEntryByObjectTableId(objectTableId : Long) : LedgerEntry?
	@Query("SELECT * FROM LedgerReferenceType WHERE LedgerReferenceType.referenceClass = :referenceTypeClass")
	suspend fun getLedgerReferenceType(referenceTypeClass: KClass<out Any>) : LedgerReferenceType?

	@Delete
	suspend fun removeEntry(ledgerEntry: LedgerEntry)
	@Query("DELETE FROM LedgerEntry WHERE LedgerEntry.objectIdInRefTable = :objectInTableId")
	suspend fun removeEntry(objectInTableId: Long)
	@Delete
	suspend fun removeReferenceAndAllEntriesOfIt(ledgerEntryReferenceType: LedgerReferenceType)
	@Query("DELETE FROM LedgerReferenceType WHERE LedgerReferenceType.referenceClass = :referenceTypeClass")
	suspend fun removeReferenceAndAllEntriesOfIt(referenceTypeClass: KClass<out Any>)

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