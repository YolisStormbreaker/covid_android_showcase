package com.yolisstorm.library_common.extensions

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.yolisstorm.library.base.R
import com.yolisstorm.library_common.utils.getColorWithVersion

fun CircularProgressButton.revertAnimationPrepared(
	@DrawableRes backgroundDrawable: Int = R.drawable.rounded_corner_white_32dp,
	@ColorRes backgroundColor: Int = R.color.button_color
) {
	revertAnimation()
	background = resources.getDrawable(backgroundDrawable, context.theme)
	backgroundTintList = getColorWithVersion(backgroundColor, resources, context)
}