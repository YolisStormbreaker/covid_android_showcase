package com.yolisstorm.data_sources.repositories.covid_stats_repo.helpers

import com.yolisstorm.data_sources.repositories.covid_stats_repo.BuildConfig
import timber.log.Timber

fun dLogIf(
	msg: String,
	isNeedLog: Boolean = BuildConfig.DEBUG && BuildConfig.GRADLE_IS_NEED_COMMON_LOG
) {
	if (isNeedLog)
		Timber.d(msg)
}

fun eLogIf(
	msg: Throwable,
	isNeedLog: Boolean = BuildConfig.DEBUG && BuildConfig.GRADLE_IS_NEED_COMMON_LOG
) {
	if (isNeedLog)
		Timber.e(msg)
}

fun eLogIf(
	msg: String,
	isNeedLog: Boolean = BuildConfig.DEBUG && BuildConfig.GRADLE_IS_NEED_COMMON_LOG
) {
	if (isNeedLog)
		Timber.e(msg)
}

fun wLogIf(
	msg: String,
	isNeedLog: Boolean = BuildConfig.DEBUG && BuildConfig.GRADLE_IS_NEED_COMMON_LOG
) {
	if (isNeedLog)
		Timber.w(msg)
}

fun wLogIf(
	msg: Throwable,
	isNeedLog: Boolean = BuildConfig.DEBUG && BuildConfig.GRADLE_IS_NEED_COMMON_LOG
) {
	if (isNeedLog)
		Timber.w(msg)
}
