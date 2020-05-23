package com.yolisstorm.live_shared_prefs

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.yolisstorm.live_shared_prefs.Extensions.get
import com.yolisstorm.live_shared_prefs.Extensions.put
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MutableLivePreference<DType : Any> constructor(
	private val clazz: Class<DType>,
	private val updatesPipeline: ReceiveChannel<String>,
	private val coroutineScope: CoroutineScope,
	private val sharedPrefs: SharedPreferences,
	private val itemKey: String,
	private val defaultValue: DType
) : MutableLiveData<DType>() {

	private var lastValue: DType? = null

	init {
		value = sharedPrefs.get(clazz, itemKey, defaultValue)
	}

	override fun postValue(value: DType?) {
		super.postValue(value)
		lastValue = value
		sharedPrefs.put(itemKey, value)
	}

	override fun setValue(value: DType?) {
		super.setValue(value)
		lastValue = value
		sharedPrefs.put(itemKey, value)
	}

	override fun onActive() {
		super.onActive()

		sharedPrefs.get(clazz, itemKey, defaultValue).let {
			if (lastValue != it) {
				lastValue = it
				postValue(it)
			}
		}


		coroutineScope.launch {
			updatesPipeline
				.receiveAsFlow()
				.collect {
					if (it == itemKey) {
						lastValue = sharedPrefs.get(clazz, it, defaultValue)
						super.postValue(lastValue)
					}
				}
		}

	}

	override fun onInactive() {
		super.onInactive()
		updatesPipeline.cancel()
	}

	companion object {
		inline fun <reified CType : Any> build(
			updatesPipeline: ReceiveChannel<String>,
			coroutineScope: CoroutineScope,
			sharedPrefs: SharedPreferences,
			itemKey: String,
			defaultValue: CType
		): MutableLivePreference<CType> =
			MutableLivePreference<CType>(
				CType::class.java,
				updatesPipeline,
				coroutineScope,
				sharedPrefs,
				itemKey,
				defaultValue
			)
	}


}

