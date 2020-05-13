package com.yolisstorm.library.store.models

import android.os.Parcelable
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KFunction
import kotlin.reflect.KSuspendFunction1

data class SourceOfTruthForSoloItem <ItemType : DispenserSimpleItem<Long>> (
	val insertItem : suspend (item: ItemType) -> Long,
	val getItem : suspend () -> ItemType?,
	val updateItem : suspend (item : ItemType) -> Unit,
	val removeItem : suspend () -> Unit
)