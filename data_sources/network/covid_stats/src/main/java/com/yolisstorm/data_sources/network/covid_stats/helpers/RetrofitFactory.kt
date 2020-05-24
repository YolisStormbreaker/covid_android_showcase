package com.yolisstorm.data_sources.network.covid_stats.helpers

import android.net.Uri
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.yolisstorm.data_sources.network.covid_stats.BuildConfig
import com.yolisstorm.data_sources.network.covid_stats.helpers.`de-serializers`.ISO2toLocaleDeserializer
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

internal object RetrofitFactory {

	private val authInterceptor = Interceptor { chain ->
		val newUrl = chain.request().url
			.newBuilder()
			.build()

		val newRequest = chain.request()
			.newBuilder()
			.url(newUrl)
			.build()

		chain.proceed(newRequest)
	}

	private val loggingInterceptor = HttpLoggingInterceptor().apply {
		level = HttpLoggingInterceptor.Level.BODY
	}

	//Not logging the authkey if not debug
	private val client =
		if (BuildConfig.DEBUG) {
			with (OkHttpClient().newBuilder()) {
				addInterceptor(authInterceptor)
				if (BuildConfig.GRADLE_IS_NEED_OK_HTTP_LOG)
					addInterceptor(loggingInterceptor)
				build()
			}
		} else {
			OkHttpClient()
				.newBuilder()
				.addInterceptor(authInterceptor)
				.build()
		}

	fun retrofit(baseUrl: String): Retrofit = Retrofit.Builder()
		.client(client)
		.baseUrl(baseUrl)
		.addConverterFactory(
			GsonConverterFactory.create(
				GsonBuilder()
					//.setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
					.registerTypeAdapter(Locale::class.java, ISO2toLocaleDeserializer())
					.create()
			)
		)
		.addCallAdapterFactory(CoroutineCallAdapterFactory())
		.build()

}