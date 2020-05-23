package com.yolisstorm.live_shared_prefs.impl

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.yolisstorm.live_shared_prefs.MutableLivePreference
import com.yolisstorm.live_shared_prefs.interfaces.ILiveSharedPrefsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class LiveSharedPrefsManager
private constructor(
	private val _sharedPrefs: SharedPreferences,
	private val scope: CoroutineScope
) : ILiveSharedPrefsManager {

	private val pipeLine: BroadcastChannel<String> = BroadcastChannel(Channel.BUFFERED)

	private val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
		scope.launch {
			pipeLine.send(key)
		}
	}

	init {
		_sharedPrefs.registerOnSharedPreferenceChangeListener(listener)
	}

	override val sharedPreferences: SharedPreferences
		get() = _sharedPrefs


	override fun <DataType : Any> get(
		clazz: Class<DataType>,
		key: String,
		defaultValue: DataType
	): MutableLiveData<DataType?> =
		MutableLivePreference(
			clazz,
			pipeLine.openSubscription(),
			scope,
			_sharedPrefs,
			key,
			defaultValue
		)

	override fun cancelSubscriptions() {
		pipeLine.cancel()
	}
}