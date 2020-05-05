package com.yolisstorm.library_common.bindingAdapters

import android.os.Build
import android.text.Html
import android.text.format.DateUtils
import android.view.View
import android.widget.TextView
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView
import com.yolisstorm.library.base.R
import com.yolisstorm.library_common.extensions.getFormattedDate
import com.yolisstorm.library_common.utils.getColorWithVersion
import timber.log.Timber
import java.text.DateFormat
import java.util.*

@BindingAdapter("app:count", "app:activeColor", requireAll = true)
fun MaterialTextView.setBadgeText(count: Int?, activeColor: Int) {
	count?.let {
		when {
			count <= 0 -> {
				setTextColor(getColorWithVersion(android.R.color.white, resources, context))
				text = "0"
			}
			count >= 1 -> {
				setTextColor(activeColor)
				text = count.toString()
			}
		}
	}
}

@BindingAdapter("app:visibilityByNull", "app:stringForReplace", requireAll = false)
fun MaterialTextView.setVisibilityByText(inputStr: String?, @StringRes stringResForReplace: Int?) {
	when {
		inputStr == null && stringResForReplace == null -> {
			visibility = View.GONE
		}
		inputStr == null && stringResForReplace != null -> {
			visibility = View.VISIBLE
			text = resources.getString(stringResForReplace)
		}
		inputStr != null -> {
			visibility = View.VISIBLE
			text = inputStr
		}
	}
}

@BindingAdapter("app:pluralValue", "app:pluralResId", requireAll = true)
fun MaterialTextView.setPlural(pluralValue: Int?, @PluralsRes pluralResId: Int?) {
	pluralValue?.let {
		pluralResId?.let {
			text = String.format(
				resources.getQuantityText(pluralResId.toInt(), pluralValue).toString(), pluralValue
			)
		}
	}
}

@BindingAdapter("app:intValue", "app:format", requireAll = true)
fun MaterialTextView.setIntValue(intValue: Int?, format: String?) {
	intValue?.let {
		format?.let {
			text = String.format(format, intValue)
		}
	}
}

@BindingAdapter("app:longValue", "app:format", requireAll = true)
fun MaterialTextView.setLongValue(longValue: Long?, format: String?) {
	longValue?.let {
		format?.let {
			text = String.format(format, longValue)
		}
	}
}

@BindingAdapter("app:currency_value", "app:prefix_res_id", "app:is_monthly", requireAll = false)
fun MaterialTextView.setCurrencyValue(
	value: Float?,
	@StringRes currencySymbolResId : Int? = R.string.default_currency_symbol,
	@StringRes prefixResId :Int? = null,
	isMonthly: Boolean? = false
) {
	value?.let {
		val currencySymbol = context.getString(currencySymbolResId ?: R.string.default_currency_symbol)
		val prefix = if (prefixResId != null) context.getString(prefixResId) else ""
		text =
			if (isMonthly == true)
				context.getString(R.string.currency_monthly_placeholder, prefix, value, currencySymbol)
			else
				context.getString(R.string.currency_placeholder, prefix, value, currencySymbol)
	}
}

@BindingAdapter("app:formattedRelativeDate")
fun MaterialTextView.setRelativeDate(relativeDate: Calendar?) {
	relativeDate?.let {
		text = DateUtils.getRelativeDateTimeString(
			context,
			it.timeInMillis,
			Calendar.getInstance().timeInMillis,
			DateUtils.DAY_IN_MILLIS,
			DateUtils.FORMAT_SHOW_WEEKDAY
		)
	}
}

@BindingAdapter("app:formattedTime", "app:isNeedDifferentBetweenNow", requireAll = false)
fun MaterialTextView.setTime(date: Calendar?, isNeedDifferentBetweenNow: Boolean?) {
	date?.let {
		var millis =
			when (isNeedDifferentBetweenNow) {
				true -> {
					date.time.time - Date().time
				}
				else -> {
					date.time.time
				}
			} + date.timeZone.rawOffset.toLong()

		val secondsInMilli = 1000L
		val minutesInMilli = secondsInMilli * 60
		val hoursInMilli = minutesInMilli * 60
		val daysInMilli = hoursInMilli * 24

		val elapsedDays = millis / daysInMilli
		millis %= daysInMilli

		val elapsedHours = millis / hoursInMilli
		millis %= hoursInMilli

		val elapsedMinutes = millis / minutesInMilli
		millis %= minutesInMilli

		val elapsedSeconds = millis / secondsInMilli

		Timber.d("realTime - ${DateFormat.getTimeInstance(DateFormat.FULL).format(Date(millis))}")
		Timber.d("days - $elapsedDays | hours - $elapsedHours | minutes - $elapsedMinutes | millis - $elapsedSeconds")
		text = resources.getString(R.string.hour_minutes_template, elapsedHours, elapsedMinutes)
	}
}

@BindingAdapter("app:formattedCalendarDate")
fun MaterialTextView.setCalendarDMY(date: Calendar?) {
	date?.let {
		setFormattedDate(it.time)
	}
}

@BindingAdapter("app:formattedDate")
fun MaterialTextView.setDateDMY(date: Date?) {
	date?.let {
		setFormattedDate(it)
	}
}

private fun MaterialTextView.setFormattedDate(date: Date) {
	text = date.getFormattedDate()
}

@BindingAdapter("app:styledHtmlText")
fun MaterialTextView.setStyledHtmlText(styledText: String?) {
	styledText?.let {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			setText(
				Html.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY),
				TextView.BufferType.SPANNABLE
			)
		} else {
			setText(Html.fromHtml(it), TextView.BufferType.SPANNABLE)
		}
	}
}

@BindingAdapter(
	"app:givenName",
	"app:middleName",
	"app:familyName",
	"app:stringResForReplace",
	requireAll = false
)
fun MaterialTextView.setFormattedName(
	givenName: String?,
	middleName: String?,
	familyName: String?,
	stringResForReplace: String? = null
) {
	Timber.d("$givenName, $familyName, $middleName")
	when {
		familyName != null && givenName != null -> {
			visibility = View.VISIBLE
			text = resources.getString(
				R.string.given_family_middle_names,
				givenName,
				familyName,
				middleName ?: ""
			)
		}
		(familyName == null || middleName == null || givenName == null) && stringResForReplace != null -> {
			visibility = View.VISIBLE
			text = stringResForReplace
		}
		else -> {
			text = ""
		}
	}

}