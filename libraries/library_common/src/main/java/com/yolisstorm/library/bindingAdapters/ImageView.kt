package com.yolisstorm.library.bindingAdapters

import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter
import com.yolisstorm.library.utils.getColorWithVersion

@BindingAdapter("app:count", "app:activeColor", requireAll = true)
fun ImageView.setCircleBadge(count: Int?, @ColorRes activeColor: Int) {
	count?.let {
		when {
			count <= 0 -> backgroundTintList =
				getColorWithVersion(android.R.color.darker_gray, resources, context)
			count >= 1 -> backgroundTintList = ColorStateList(
				arrayOf(intArrayOf(android.R.attr.state_enabled)),
				intArrayOf(activeColor)
			)
		}
	}
}