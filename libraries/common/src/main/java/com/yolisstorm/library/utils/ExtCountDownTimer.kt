package com.yolisstorm.library.utils

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.reflect.KProperty
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
class ExtCountDownTimer constructor(
	private val duration : Duration,
	private val countDownInterval : Duration
) {

	operator fun getValue(thisRef: Any?, property: KProperty<*>): ExtCountDownTimer {
		return ExtCountDownTimer(duration, countDownInterval)
	}

	private val _timerStarted = MutableLiveData<Event<Boolean>>()
	val timerStarted : LiveData<Event<Boolean>>
	    get() = _timerStarted

	private val _timerTick = MutableLiveData<Event<Unit>>()
	val timerTick : LiveData<Event<Unit>>
	    get() = _timerTick

	private val _timerFinished = MutableLiveData<Event<Boolean>>()
	val timerFinished : LiveData<Event<Boolean>>
	    get() = _timerFinished

	fun startTimer() {
		timer.start()
		_timerStarted.value = Event(true)
		_timerFinished.value = Event(false)
	}

	fun finishTimer() {
		timer.cancel()
	}

	fun restartTimer() {
		finishTimer()
		startTimer()
	}

	private val timer: CountDownTimer =
		object : CountDownTimer(duration.toLongMilliseconds(), countDownInterval.toLongMilliseconds()) {
			override fun onTick(millisUntilFinished: Long) {
				_timerTick.value = Event(Unit)
			}
			override fun onFinish() {
				_timerFinished.value = Event(true)
			}
		}



}