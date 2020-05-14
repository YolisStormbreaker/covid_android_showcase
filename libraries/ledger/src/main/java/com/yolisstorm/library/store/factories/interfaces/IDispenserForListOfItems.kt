package com.yolisstorm.library.store.factories.interfaces

import androidx.paging.PagedList
import com.yolisstorm.library.store.models.DispenserResponse
import com.yolisstorm.library.store.models.DispenserSimpleItem
import kotlinx.coroutines.flow.Flow

interface IDispenserForListOfItems<in ItemIdType : Long,
		ItemType : DispenserSimpleItem<Long>> {

	suspend fun getItemsListFromStorage(): DispenserResponse<PagedList<ItemType>>
	suspend fun updateItemsListInStorage(newList: List<ItemType>)
	suspend fun flushObjectsFromStorage()

	suspend fun getItemFromStorage(itemId: ItemIdType): Flow<DispenserResponse<ItemType>>
	suspend fun updateItemInStorage(item: ItemType)
	suspend fun removeItemFromStorage(item: ItemType)

}