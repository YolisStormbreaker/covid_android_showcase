package com.yolisstorm.library.store.factories

import kotlin.time.*

/**
 * @param expireAfterWrite
 * Длительность по истечении которой данные считаются просроченными
 * Сбрасывается перезаписью данных в источнике данных
 * @param expireAfterRead
 * Длительность по истечении которой данные считаются долго не нужными
 * Сбрасывается запросом на чтение данных, либо на их перезапись
 */
@ExperimentalTime
data class DispenserStoreStrategy(
	val expireAfterWrite: Duration,
	val expireAfterRead: Duration
) {
	companion object {
		fun getDefault(
			expireAfterWrite: Duration = 30.days,
			expireAfterRead: Duration = 15.days
		) =
			DispenserStoreStrategy(
				expireAfterWrite = expireAfterWrite,
				expireAfterRead = expireAfterRead
			)
	}
}