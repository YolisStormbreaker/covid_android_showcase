package com.yolisstorm.library_common.extensions

import android.graphics.Color
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.markRequiredInRed() {
	hint = buildSpannedString {
		append(hint)
		color(Color.RED) { append(" *") } // Mind the space prefix.
	}
}