package com.yolisstorm.library_common.utils

import android.content.Context
import androidx.annotation.RawRes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getJsonStringFromFile(context: Context?, @RawRes jsonRawId: Int): String? =
	withContext(Dispatchers.Default) {
		context?.resources?.openRawResource(jsonRawId)?.bufferedReader().use { it?.readText() }
	}