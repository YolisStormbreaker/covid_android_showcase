package com.yolisstorm.library.store.ledger_lair_database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class LedgerEntry (
	@PrimaryKey(autoGenerate = true)
	val entryId: Long = 0,
	@PrimaryKey(autoGenerate = false)
	val objectIdInRefTable: Long,
	val referenceTypeId: Long,
	val creationDate : Date = Date(),
	var lastUpdatedDate : Date = Date(),
	var lastReadDate: Date? = null,
	var checkSum : Int
)