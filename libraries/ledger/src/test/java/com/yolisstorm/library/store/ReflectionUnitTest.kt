package com.yolisstorm.library.store

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yolisstorm.library.store.ledger_lair_database.entities.LedgerEntry
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.koin.ext.getFullName
import org.koin.ext.getScopeName
import java.util.*
import kotlin.reflect.KClass


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ReflectionUnitTest {

	@Test
	fun testTypeVisaVersaConversion() {
		val type : KClass<LedgerEntry> = LedgerEntry::class
		val typeInString = type.getFullName()
		val recoveredType : Any? = Class.forName(typeInString).kotlin
		val valueToTest = LedgerEntry(
			entryId = 0L,
			objectIdInRefTable = 0L,
			referenceTypeId = 0L,
			creationDate = Date(),
			lastUpdatedDate = Date(),
			lastReadDate = Date(),
			checkSum = 0
		)
		assertEquals(valueToTest.javaClass.kotlin, recoveredType)
	}

}