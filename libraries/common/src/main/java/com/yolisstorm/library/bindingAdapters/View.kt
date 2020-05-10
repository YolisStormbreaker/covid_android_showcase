package com.yolisstorm.library.bindingAdapters

import android.view.View
import androidx.databinding.BindingAdapter
import timber.log.Timber

@BindingAdapter("app:visibility")
fun View.setVisibility(visibilityBool: Boolean?) {
	visibility = when (visibilityBool) {
		true -> {
			Timber.d("Set view visible by BindingAdapter"); View.VISIBLE
		}
		false -> {
			Timber.d("Set view invisible by BindingAdapter"); View.INVISIBLE
		}
		else -> {
			Timber.d("Set view gone by BindingAdapter"); View.GONE
		}
	}
}