package com.yolisstorm.library_common.bindingAdapters

import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.yolisstorm.library_common.extensions.capsFirstLetter
import timber.log.Timber

@BindingAdapter(
	"chips",
	"templateId",
	"isUpperCase",
	"isCheckable",
	"isClickable",
	requireAll = false
)
fun ChipGroup.prepareChips(
	chips: List<String>?,
	@LayoutRes templateId: Int,
	isUpperCase: Boolean = true,
	isCheckable: Boolean = false,
	isClickable: Boolean = false
) {
	removeAllViews()
	Timber.d("ChipGroup.prepareChips - $chips")
	chips?.forEach { title ->
		val chip = (LayoutInflater.from(context).inflate(templateId, null) as Chip)
			.also {
				it.text =
					if (isUpperCase) title.toUpperCase(resources.configuration.locale) else title.capsFirstLetter()
				it.isCheckable = isCheckable
				it.isClickable = isClickable
				it.isEnabled = true
				it.isActivated = true
			}
		addView(chip)
	}
}