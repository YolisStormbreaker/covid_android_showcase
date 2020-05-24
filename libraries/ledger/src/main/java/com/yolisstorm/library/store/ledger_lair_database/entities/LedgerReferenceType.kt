package com.yolisstorm.library.store.ledger_lair_database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlin.reflect.KClass

@Entity (
	/*foreignKeys = [
		ForeignKey(
			entity = LedgerEntry::class,
			parentColumns = ["referenceTypeId"],
			childColumns = ["referenceId"],
			onDelete = ForeignKey.CASCADE,
			onUpdate = ForeignKey.RESTRICT
		)
	]*/
)
data class LedgerReferenceType (
	@PrimaryKey(autoGenerate = true)
	val referenceId: Long = 0,
	val referenceClass: KClass<out Any>
)