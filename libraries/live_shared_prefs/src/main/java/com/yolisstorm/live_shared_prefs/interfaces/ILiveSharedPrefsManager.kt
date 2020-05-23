package com.yolisstorm.live_shared_prefs.interfaces

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData

interface ILiveSharedPrefsManager {

	val sharedPreferences: SharedPreferences

	fun <DataType : Any> get(
		clazz: Class<DataType>,
		key: String,
		defaultValue: DataType
	): MutableLiveData<DataType>

	fun cancelSubscriptions()

}