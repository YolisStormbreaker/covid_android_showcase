package com.yolisstorm.live_shared_prefs

import android.content.SharedPreferences
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken

object Extensions {

	inline fun <reified DataType: Any?> SharedPreferences.get(key: String, defaultValue: DataType) =
		get(DataType::class.java, key, defaultValue)

	fun <DataType : Any?> SharedPreferences.get(
		clazz: Class<DataType>,
		key: String, defaultValue: DataType) : DataType? =
		when (defaultValue) {
			is String -> {
				getString(key, defaultValue) as DataType?
			}
			is Int -> {
				getInt(key, defaultValue) as DataType?
			}
			is Long -> {
				getLong(key, defaultValue) as DataType?
			}
			is Float -> {
				getFloat(key, defaultValue) as DataType?
			}
			is Boolean -> {
				getBoolean(key, defaultValue) as DataType?
			}
			else -> {
				Gson().fromJson(
					getString(
						key,
						Gson().toJson(null)
					),
					clazz
				)
			}
		}



	fun <DataType: Any?> SharedPreferences.put(key: String, value: DataType?) {
		with (edit()) {
			when (value) {
				is String? -> {
					putString(key, value)
				}
				is Int -> {
					putInt(key, value)
				}
				is Long -> {
					putLong(key, value)
				}
				is Float -> {
					putFloat(key, value)
				}
				is Boolean -> {
					putBoolean(key, value)
				}
				else -> {
					putString(key, Gson().toJson(value))
				}
			}
			apply()
		}
	}

	inline fun <reified T> Gson.fromJson(json: String?): T? = fromJson<T>(json, object: TypeToken<T>() {}.type)


}