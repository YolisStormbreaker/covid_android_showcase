package com.yolisstorm.library_common.extensions

import java.lang.IndexOutOfBoundsException
import java.util.NoSuchElementException
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties

/**
 * Returns second element.
 * @throws [NoSuchElementException] if the list is empty.
 * @throws [IndexOutOfBoundsException] index of bounds exception.
 */
fun <T : Any, C : Collection<T>> C.second(): T =
	when {
		size <= 0 -> throw NoSuchElementException("")
		size < 2 -> throw IndexOutOfBoundsException("")
		else -> this.elementAt(1)
	}

/**
 * Returns third element.
 * @throws [NoSuchElementException] if the list is empty.
 * @throws [IndexOutOfBoundsException] index of bounds exception.
 */
fun <T : Any, C : Collection<T>> C.third(): T =
	when {
		size <= 0 -> throw NoSuchElementException("")
		size < 3 -> throw IndexOutOfBoundsException("")
		else -> this.elementAt(2)
	}

@Throws(IllegalAccessException::class, ClassCastException::class)
inline fun <reified T> Any.getField(fieldName: String): T? {
	this::class.memberProperties.forEach { kCallable ->
		if (fieldName == kCallable.name) {
			return kCallable.getter.call(this) as T?
		}
	}
	return null
}

inline fun <reified C : Any> C.classToReadableString(): String {
	val stringBuffer = StringBuffer()
	stringBuffer.append("\n { \n")
	C::class.memberProperties.forEach {
		if (it.visibility == KVisibility.PUBLIC) {
			stringBuffer.append("\n   ")
			stringBuffer.append("${it.name} - ${it.returnType} - ${it.getter.call(this)}")
		}
	}
	stringBuffer.append("\n } \n")
	return stringBuffer.toString()
}