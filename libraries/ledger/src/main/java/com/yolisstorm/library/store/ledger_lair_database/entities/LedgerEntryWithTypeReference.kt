package com.yolisstorm.library.store.ledger_lair_database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class LedgerEntryWithTypeReference<T : Any> (
	@Embedded val typeReference: LedgerReferenceType<T>,
	@Relation (
		parentColumn = "referenceId",
		entityColumn = "referenceTypeId"
	)
	val entriesList : List<LedgerEntry>
)