package com.yolisstorm.library.store.factories.impl

import android.os.Parcelable
import com.yolisstorm.library.store.factories.StoreCacheStrategy
import com.yolisstorm.library.store.factories.interfaces.IStoreForSoloItem
import com.yolisstorm.library.store.models.StoreResponse
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass
import kotlin.time.ExperimentalTime

@ExperimentalTime
class StoreForSoloItemFactory<T : Parcelable> constructor(
	private val cls: KClass<T>,
	private val cacheStrategy: StoreCacheStrategy = StoreCacheStrategy.getDefault()
) : IStoreForSoloItem<T> {

	override suspend fun getItemFromStore(): Flow<StoreResponse<T>> {
		throw NotImplementedError()
	}

	override suspend fun updateItemInStore(newItem: T) {
		throw NotImplementedError()
	}

	override suspend fun eraseItemFromStore() {
		throw NotImplementedError()
	}

	private fun checkIfCacheItemIsFresh(): Pair<Boolean, T?> {

	}

}