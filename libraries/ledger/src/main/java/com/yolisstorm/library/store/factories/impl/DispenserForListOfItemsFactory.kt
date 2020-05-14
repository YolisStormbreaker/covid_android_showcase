package com.yolisstorm.library.store.factories.impl

import androidx.paging.PagedList
import com.yolisstorm.library.store.factories.DispenserStoreStrategy
import com.yolisstorm.library.store.factories.interfaces.IDispenserForListOfItems
import com.yolisstorm.library.store.ledger_lair_database.dao.LedgerOperationsDao
import com.yolisstorm.library.store.models.DispenserResponse
import com.yolisstorm.library.store.models.DispenserSimpleItem
import com.yolisstorm.library.store.models.SourceOfTruthForListOfItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent.get
import kotlin.reflect.KClass
import kotlin.time.ExperimentalTime

@ExperimentalTime
class DispenserForListOfItemsFactory<ItemType : DispenserSimpleItem<Long>>(
	private val cls: KClass<ItemType>,
	private val sourceOfTruthForListOfItems: SourceOfTruthForListOfItems<ItemType>,
	private val ledgerProvider: LedgerOperationsDao,
	private val dispenserStrategy: DispenserStoreStrategy = DispenserStoreStrategy.getDefault()
) : IDispenserForListOfItems<Long, ItemType> {

	companion object {
		inline fun <reified ItemTypeC : DispenserSimpleItem<Long>> buildSmartDispenserForListOfItems(
			sourceOfTruthForListOfItems: SourceOfTruthForListOfItems<ItemTypeC>
		): IDispenserForListOfItems<Long, ItemTypeC> =
			DispenserForListOfItemsFactory(
				ItemTypeC::class,
				sourceOfTruthForListOfItems,
				get(LedgerOperationsDao::class.java)
			)
	}

	override suspend fun getItemsListFromStorage(): DispenserResponse<PagedList<ItemType>> {

	}

	override suspend fun updateItemsListInStorage(newList: List<ItemType>) {
		throw NotImplementedError()
	}

	override suspend fun flushObjectsFromStorage() {
		ledgerProvider.removeReferenceAndAllEntriesOfIt(cls)
		sourceOfTruthForListOfItems.removeAllItems()
	}

	override suspend fun getItemFromStorage(itemId: Long): Flow<DispenserResponse<ItemType>> {
		throw NotImplementedError()
	}

	override suspend fun updateItemInStorage(item: ItemType) {
		throw NotImplementedError()
	}

	override suspend fun removeItemFromStorage(item: ItemType) {
		ledgerProvider.removeEntry(item.id)
		sourceOfTruthForListOfItems.removeSoloItem(item)
	}
}