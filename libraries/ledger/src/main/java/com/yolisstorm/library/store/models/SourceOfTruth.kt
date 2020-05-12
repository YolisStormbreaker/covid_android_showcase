package com.yolisstorm.library.store.models

import android.os.Parcelable
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KFunction
import kotlin.reflect.KSuspendFunction1

data class SourceOfTruth <T : Parcelable> (
	val insert : KSuspendFunction1<T, Unit>,
	val update : KSuspendFunction1<T, Unit>,
	val select : KFunction<Flow<T>>,
	val remove : KFunction<Unit>
)