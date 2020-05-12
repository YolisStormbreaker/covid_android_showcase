package com.yolisstorm.library.store.factories.interfaces

import com.yolisstorm.library.store.models.StoreResponse
import com.yolisstorm.library.store.models.StoreSimpleItem
import kotlinx.coroutines.flow.Flow

interface IDistributorForItemsList<in D, T> {

	suspend fun getListFromStore() : Flow<StoreResponse<List<T>>>
	suspend fun updateListInStore(newList: List<T>)
	suspend fun flushListInStore()

	suspend fun getItemFromStore(itemId: D) : Flow<StoreResponse<T>>
	suspend fun updateItemInStore(item: T)

}