package com.yolisstorm.library.store.factories

import kotlin.time.*

@ExperimentalTime
data class StoreCacheStrategy(
	val expireAfterWrite: Duration
) {
	companion object {
		fun getDefault(
			expireAfterWrite: Duration = 10.minutes
		) =
			StoreCacheStrategy(
				expireAfterWrite = expireAfterWrite
			)
	}
}