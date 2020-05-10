package com.yolisstorm.library.store.ledger_lair_database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*


@Entity(
	indices = [
		Index(
			value = ["id"], unique = true
		)
	]
)
data class LedgerEntry (
	@PrimaryKey(autoGenerate = true)
	val id: UUID,
	val referenceTableId: UUID,
	val creationDate : Date = Date(),
	var lastUpdateDate : Date,
	var checkSum : Int
)