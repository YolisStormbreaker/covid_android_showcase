package com.yolisstorm.library.store.factories.impl

import android.os.Parcelable
import com.yolisstorm.library.store.factories.StoreCacheStrategy
import com.yolisstorm.library.store.ledger_lair_database.dao.LedgerOperationsDao
import com.yolisstorm.library.store.factories.interfaces.IDistributorForSoloItem
import com.yolisstorm.library.store.models.SourceOfTruth
import com.yolisstorm.library.store.models.StoreResponse
import kotlinx.coroutines.flow.Flow
import org.koin.java.KoinJavaComponent.get
import kotlin.reflect.KClass
import kotlin.time.ExperimentalTime

@ExperimentalTime
class DistributorForSoloItemFactory<ItemType : Parcelable> constructor(
	private val cls: KClass<ItemType>,
	private val sourceOfTruth: SourceOfTruth<ItemType>,
	private val ledgerProvider : LedgerOperationsDao,
	private val cacheStrategy: StoreCacheStrategy = StoreCacheStrategy.getDefault()
) : IDistributorForSoloItem<ItemType> {

	companion object {
		inline fun <reified ItemTypeC : Parcelable> buildForSoloItem(
			sourceOfTruth: SourceOfTruth<ItemTypeC>
		) : IDistributorForSoloItem<ItemTypeC> =
			DistributorForSoloItemFactory(
				ItemTypeC::class,
				sourceOfTruth,
				get(LedgerOperationsDao::class.java))
	}

	override suspend fun getItemFromStorage(): Flow<StoreResponse<ItemType>> {
		throw NotImplementedError()
	}

	override suspend fun updateItemInStorage(newItem: ItemType) {
		throw NotImplementedError()
	}

	override suspend fun eraseItemFromStorage() {
		throw NotImplementedError()
	}

}