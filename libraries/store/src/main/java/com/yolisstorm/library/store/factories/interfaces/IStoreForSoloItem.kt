package com.yolisstorm.library.store.factories.interfaces

import com.yolisstorm.library.store.models.StoreResponse
import kotlinx.coroutines.flow.Flow

interface IStoreForSoloItem<T> {

	suspend fun getItemFromStore() : Flow<StoreResponse<T>>
	suspend fun updateItemInStore(newItem: T)
	suspend fun eraseItemFromStore()

}