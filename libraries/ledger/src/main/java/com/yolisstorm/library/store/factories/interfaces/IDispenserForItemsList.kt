package com.yolisstorm.library.store.factories.interfaces

import com.yolisstorm.library.store.models.DispenserResponse
import kotlinx.coroutines.flow.Flow

interface IDispenserForItemsList<in D, T> {

	suspend fun getListFromStore() : Flow<DispenserResponse<List<T>>>
	suspend fun updateListInStore(newList: List<T>)
	suspend fun flushListInStore()

	suspend fun getItemFromStore(itemId: D) : Flow<DispenserResponse<T>>
	suspend fun updateItemInStore(item: T)

}