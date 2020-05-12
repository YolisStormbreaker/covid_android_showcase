package com.yolisstorm.library.store.ledger_lair_database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class LedgerEntryWithTypeReference (
	@Embedded val typeReference: LedgerReferenceType,
	@Relation (
		parentColumn = "referenceId",
		entityColumn = "referenceTypeId"
	)
	val entriesList : List<LedgerEntry>
)