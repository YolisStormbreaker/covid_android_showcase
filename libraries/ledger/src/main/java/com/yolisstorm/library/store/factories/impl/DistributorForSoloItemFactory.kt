package com.yolisstorm.library.store.factories.impl

import android.os.Parcelable
import com.yolisstorm.library.common.resultWrappers.network.NetworkResultWrapper
import com.yolisstorm.library.store.factories.StoreCacheStrategy
import com.yolisstorm.library.store.ledger_lair_database.dao.LedgerOperationsDao
import com.yolisstorm.library.store.factories.interfaces.IStoreForSoloItem
import com.yolisstorm.library.store.models.StoreResponse
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KSuspendFunction1
import kotlin.time.ExperimentalTime

@ExperimentalTime
class StoreForSoloItemFactory<T : Parcelable> constructor(
	private val cls: KClass<T>,
	private val getItemFromStoreProvider : KFunction<Flow<StoreResponse<T>>>,
	private val updateItemIntoStoreProvider : KSuspendFunction1<T, Unit>,
	private val getItemFromRemoteSourceProvider : KFunction<Flow<NetworkResultWrapper<T>>>,
	private val ledgerProvider : LedgerOperationsDao<>
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

}