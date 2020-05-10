package com.yolisstorm.library.bindingAdapters

import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.yolisstorm.library.extensions.capsFirstLetter
import timber.log.Timber
import java.util.*

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
					if (isUpperCase) title.toUpperCase(Locale.getDefault()) else title.capsFirstLetter()
				it.isCheckable = isCheckable
				it.isClickable = isClickable
				it.isEnabled = true
				it.isActivated = true
			}
		addView(chip)
	}
}