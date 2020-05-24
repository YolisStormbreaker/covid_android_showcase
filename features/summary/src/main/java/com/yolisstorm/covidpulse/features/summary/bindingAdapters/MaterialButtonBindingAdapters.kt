package com.yolisstorm.covidpulse.features.summary.bindingAdapters

import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.yolisstorm.covidpulse.features.summary.R
import java.util.*

@BindingAdapter("app:location")
fun MaterialButton.setTitleAndIconCorrespondingLocale(locale: Locale?) {
	text = when (locale) {
		null -> resources.getString(R.string.unknown_location)
		else -> locale.displayCountry
	}
	icon = when (locale) {
		null -> resources.getDrawable(R.drawable.ic_undefined_location_24, context.theme)
		else -> resources.getDrawable(R.drawable.ic_baseline_location_on_24, context.theme)
	}
}