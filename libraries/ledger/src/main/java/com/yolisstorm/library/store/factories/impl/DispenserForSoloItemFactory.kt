package com.yolisstorm.library.store.factories.impl

import com.yolisstorm.library.store.factories.DispenserStoreStrategy
import com.yolisstorm.library.store.factories.interfaces.IDispenserForSoloItem
import com.yolisstorm.library.store.ledger_lair_database.dao.LedgerOperationsDao
import com.yolisstorm.library.store.ledger_lair_database.entities.LedgerEntry
import com.yolisstorm.library.store.ledger_lair_database.entities.LedgerReferenceType
import com.yolisstorm.library.store.models.DispenserResponse
import com.yolisstorm.library.store.models.DispenserSimpleItem
import com.yolisstorm.library.store.models.SourceOfTruthForSoloItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent.get
import timber.log.Timber
import java.util.*
import kotlin.reflect.KClass
import kotlin.time.ExperimentalTime

@ExperimentalTime
class DispenserForSoloItemFactory<ItemType : DispenserSimpleItem<Long>> constructor(
	private val cls: KClass<ItemType>,
	private val sourceOfTruthForSoloItem: SourceOfTruthForSoloItem<ItemType>,
	private val ledgerProvider: LedgerOperationsDao,
	private val dispenserStrategy: DispenserStoreStrategy = DispenserStoreStrategy.getDefault()
) : IDispenserForSoloItem<ItemType> {

	companion object {
		inline fun <reified ItemTypeC : DispenserSimpleItem<Long>> buildSmartDispenserForSoloItem(
			sourceOfTruthForSoloItem: SourceOfTruthForSoloItem<ItemTypeC>
		): IDispenserForSoloItem<ItemTypeC> =
			DispenserForSoloItemFactory(
				ItemTypeC::class,
				sourceOfTruthForSoloItem,
				get(LedgerOperationsDao::class.java)
			)
	}

	@Throws(java.lang.IllegalArgumentException::class)
	override suspend fun getItemFromStorage(): Flow<DispenserResponse<ItemType>> =
		flow {
			val referenceType = ledgerProvider.getLedgerReferenceType(cls)
			if (referenceType == null)
				emit(DispenserResponse.NotFound)
			val entries =
				ledgerProvider.getLedgersEntriesWithReferenceType(referenceType!!.referenceId)
			when {
				entries.entriesList.size > 1 -> {
					Timber.e("getItemFromStorage() - refType = $referenceType - Entries more than one. Logic error!")
					throw IllegalArgumentException()
				}
				entries.entriesList.size == 1 -> {
					val item = sourceOfTruthForSoloItem.getItem()
					if (item == null) {
						Timber.e(
							"""checkIfCacheItemIsFresh(
								|item = $item
								|ledgerEntry = ${entries.entriesList.first()}
								) - Ledger Entry exist but item in database is missing
							""".trimMargin()
						)
						throw IllegalArgumentException()
					}
					val resultResponse =
						if (checkIfItemIsFresh(entries.entriesList.first()))
							DispenserResponse.Fresh(item)
						else
							DispenserResponse.Spoiled(item)
					ledgerProvider.updateLedgerEntry(
						entries.entriesList.first().copy(
							lastReadDate = Date()
						)
					)
					emit(resultResponse)
				}
			}
		}

	@Throws(java.lang.IllegalArgumentException::class)
	private fun checkIfItemIsFresh(ledgerEntry: LedgerEntry): Boolean =
		((Date().time - ledgerEntry.lastUpdatedDate.time) <=
				dispenserStrategy.expireAfterWrite.inMilliseconds)


	@Throws(java.lang.IllegalArgumentException::class)
	override suspend fun updateItemInStorage(newItem: ItemType) {
		when (ledgerProvider.getLedgerEntryByObjectTableId(newItem.id)) {
			null -> {
				val referenceTypeId =
					ledgerProvider.getLedgerReferenceType(cls)?.referenceId
						?: ledgerProvider.insertLedgerReferenceType(
							LedgerReferenceType(
								referenceClass = cls
							)
						)
				if (referenceTypeId == -1L) {
					Timber.e("updateItemInStorage(newItem = $newItem) - Fail to create or find referenceType")
					throw IllegalArgumentException()
				}
				val entriesWithType =
					ledgerProvider.getLedgersEntriesWithReferenceType(referenceTypeId)
				when {
					entriesWithType.entriesList.size > 1 -> {
						Timber.e("updateItemInStorage(newItem = $newItem) - Entries more than one. Logic error!")
						throw IllegalArgumentException()
					}
					entriesWithType.entriesList.size == 1 -> {
						entriesWithType.entriesList.first().also { entry ->
							ledgerProvider.insertOrUpdateNewLedgerEntry(
								referenceType = entriesWithType.typeReference,
								ledgerEntry = if (entry.checkSum != newItem.hashCode()) {
									entry.copy(
										lastUpdatedDate = Date()
									)
								} else {
									sourceOfTruthForSoloItem.updateItem(newItem)
									entry.copy(
										lastUpdatedDate = Date(),
										checkSum = newItem.hashCode()
									)
								}
							)
						}
					}
					entriesWithType.entriesList.isEmpty() -> {
						sourceOfTruthForSoloItem.insertItem(newItem)
						ledgerProvider.insertLedgerEntry(
							LedgerEntry(
								objectIdInRefTable = newItem.id,
								referenceTypeId = entriesWithType.typeReference.referenceId,
								checkSum = newItem.hashCode()
							)
						)
					}
				}
			}
		}
	}

	override suspend fun eraseItemFromStorage() {
		ledgerProvider.removeReferenceAndAllEntriesOfIt(cls)
		sourceOfTruthForSoloItem.removeItem()
	}

}