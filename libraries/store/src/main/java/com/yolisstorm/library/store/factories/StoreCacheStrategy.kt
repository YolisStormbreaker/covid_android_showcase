package com.yolisstorm.library.store.factories

import kotlin.time.*

@ExperimentalTime
data class StoreCacheStrategy(
	val expireAfterWrite: Duration
) {
	companion object {
		fun getDefault(
			expireAfterWrite: Duration = Duration.INFINITE
		) =
			StoreCacheStrategy(
				expireAfterWrite = expireAfterWrite
			)
	}
}