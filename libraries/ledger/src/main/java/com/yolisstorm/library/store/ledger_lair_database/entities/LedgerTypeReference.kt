package com.yolisstorm.library.store.ledger_lair_database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.reflect.KClass

@Entity
data class LedgerReferenceType (
	@PrimaryKey(autoGenerate = true)
	val referenceId: Long,
	val referenceClass: KClass<out Any>
)