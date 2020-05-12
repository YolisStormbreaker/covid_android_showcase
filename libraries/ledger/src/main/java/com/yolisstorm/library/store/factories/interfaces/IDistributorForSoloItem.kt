package com.yolisstorm.library.store.factories.interfaces

import com.yolisstorm.library.store.models.StoreResponse
import kotlinx.coroutines.flow.Flow

interface IDistributorForSoloItem<T> {

	suspend fun getItemFromStorage() : Flow<StoreResponse<T>>
	suspend fun updateItemInStorage(newItem: T)
	suspend fun eraseItemFromStorage()

}