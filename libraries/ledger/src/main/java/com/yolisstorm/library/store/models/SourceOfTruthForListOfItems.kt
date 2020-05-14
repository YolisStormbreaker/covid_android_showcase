package com.yolisstorm.library.store.models

import androidx.paging.PagedList

data class SourceOfTruthForListOfItems <ItemType : DispenserSimpleItem<Long>> (
	val insertCollection : suspend (list: Collection<ItemType>) -> Unit,
	val getPagedList : suspend () -> PagedList<ItemType>,
	val getSoloItem : suspend (itemId: Long) -> ItemType?,
	val updateItemsInCollection : suspend (list: Collection<ItemType>) -> Unit,
	val updateSoloItem : suspend (item : ItemType) -> Unit,
	val removeAllItems : suspend () -> Unit,
	val removeSoloItem : suspend (item : ItemType) -> Unit
)