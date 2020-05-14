package com.yolisstorm.library.store.factories.interfaces

import com.yolisstorm.library.store.models.DispenserResponse
import kotlinx.coroutines.flow.Flow

interface IDispenserForSoloItem<T> {

	suspend fun getItemFromStorage() : Flow<DispenserResponse<T>>
	suspend fun updateItemInStorage(newItem: T)
	suspend fun eraseItemFromStorage()

}