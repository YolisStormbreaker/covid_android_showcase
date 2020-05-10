package com.yolisstorm.library.utils

import android.util.Base64
import java.io.UnsupportedEncodingException

fun getAuthToken(login: String, pswd: String): String {
	var data = ByteArray(0)
	try {
		data = ("$login:$pswd").toByteArray(charset("UTF-8"))
	} catch (e: UnsupportedEncodingException) {
		e.printStackTrace()
	}
	return "Basic " + Base64.encodeToString(data, Base64.NO_WRAP)
}