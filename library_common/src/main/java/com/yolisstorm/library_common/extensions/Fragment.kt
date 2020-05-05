package com.yolisstorm.library_common.extensions

import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.yolisstorm.library.base.R

fun Fragment.startNewWebIntent(url: String) =
	url.also {
		var actualUrl = it
		if (!it.startsWith("http://") && !it.startsWith("https://"))
			actualUrl = "http://$it"
		Intent(Intent.ACTION_VIEW, Uri.parse(actualUrl)).also { intent ->
			activity?.startActivity(intent)
		}
	}

fun Fragment.startNewPhoneIntent(phone: String) =
	phone.also {
		Intent(Intent.ACTION_DIAL).also { intent ->
			intent.data = Uri.parse("tel:$it")
			activity?.startActivity(intent)
		}
	}

fun Fragment.startNewMailIntent(mail: String) =
	mail.also {
		Intent(Intent.ACTION_SEND).also { intent ->
			intent.data = Uri.parse("mailto:")
			intent.type = "text/plain"
			intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(mail))
			intent.putExtra(
				Intent.EXTRA_SUBJECT,
				"${context?.getString(R.string.support_mail_subject)}"
			)
			activity?.startActivity(
				Intent.createChooser(
					intent,
					context?.getString(R.string.support_email_title)
				)
			)
		}
	}

fun Fragment.startNewInstagramProfileIntent(username: String) {
	requireContext().run {
		startIntent(
			packageName = getString(R.string.instagram_package_name),
			url = getString(R.string.instagram_url),
			username = username,
			intentTitle = R.string.support_instagram_title
		)
	}
}

fun Fragment.startNewWhatsappProfileIntent(username: String) {
	requireContext().run {
		startIntent(
			packageName = getString(R.string.whatsapp_package_name),
			url = getString(R.string.whatsapp_url),
			username = username,
			intentTitle = R.string.support_whatsapp_title
		)
	}
}

fun Fragment.startNewViberProfileIntent(username: String) {
	requireContext().run {
		startIntent(
			packageName = getString(R.string.viber_package_name),
			url = getString(R.string.viber_url),
			username = username,
			intentTitle = R.string.support_viber_title
		)
	}
}

fun Fragment.startNewTelegramProfileIntent(username: String) {
	requireContext().run {
		startIntent(
			packageName = getString(R.string.telegram_package_name),
			url = getString(R.string.telegram_url),
			username = username,
			intentTitle = R.string.support_telegram_title
		)
	}
}

fun Fragment.startIntent(
	packageName: String,
	url: String,
	username: String,
	@StringRes intentTitle: Int
) {
	Intent(Intent.ACTION_VIEW).also { intent ->
		intent.data = Uri.parse("$url$username")
		if (context?.isAppAvailable(packageName) == true) {
			intent.setPackage(packageName)
		}
		activity?.startActivity(Intent.createChooser(intent, context?.getString(intentTitle)))
	}
}