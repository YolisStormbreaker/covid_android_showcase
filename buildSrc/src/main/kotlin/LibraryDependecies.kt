

private object LibraryVersion {

	const val ACTIVITY                      =   "1.1.0"
	const val APPCOMPAT                     =   "1.2.0-beta01"
	const val BTN_WITH_CIRCLE_LOADER        =   "2.2.0"
	const val CIRCLE_IMAGEVIEW              =   "3.0.1"
	const val CONSTRAINT_LAYOUT             =   "2.0.0-beta4"
	const val CORE_KTX                      =   "1.3.0-rc01"
	const val FIREBASE_CRASHLYTICS          =   "2.10.1"
	const val FIREBASE_CORE                 =   "17.2.0"
	const val FIREBASE_KTX                  =   "19.1.0"
	const val FIREBASE_MESSAGING            =   "20.0.0"
	const val FLOATING_SEARCH_VIEW          =   "2.1.1"
	const val FRAGMENT                      =   "1.2.2"
	const val GLIDE                         =   "4.11.0"
	const val G_MAPS                        =   "17.0.0"
	const val G_MAPS_KTX                    =   "0.3.1"
	const val G_MAPS_LOCATION               =   "17.0.0"
	const val G_MAPS_PLACES                 =   "2.2.0"
	const val G_MAPS_UTILS_KTX              =   "0.3.1"
	const val GMS_GOOGLE_SERVICES           =   "4.3.3"
	const val GSON                          =   "2.8.6"
	const val KOIN                          =   "2.1.3"
	const val LEAKCANARY                    =   "2.2"
	const val LIFECYCLE                     =   "2.3.0-alpha02"
	const val MATERIAL_CALENDAR             =   "1.7.0"
	const val MATERIAL                      =   "1.2.0-alpha06"
	const val OKHTTP_3                      =   "4.6.0"
	const val OKHTTP_LOGGING_INTERCEPTOR    =   "4.6.0"
	const val PAGING                        =   "2.1.2"
	const val PLAY_CORE                     =   "1.7.2"
	const val RECYCLER_VIEW_SELECTION       =   "1.1.0-rc01"
	const val RECYCLER_VIEW                 =   "1.2.0-alpha03"
	const val RETROFIT                      =   "2.7.2"
	const val ROOM                          =   "2.2.4"
	const val ROUNDED_IMAGEVIEW             =   "2.3.0"
	const val SERIALIZATION_RUNTIME         =   "0.20.0"
	const val SHIMMER                       =   "0.5.0"
	const val SUPPORT                       =   "1.1.0"
	const val SUPPORT_CARD_VIEW             =   "1.0.0"
	const val SUPPORT_CORE_UTILS            =   "1.0.0"
	const val TIMBER                        =   "4.7.1"
	const val WORK_MANAGER                  =   "2.3.2"

}

object LibraryDependecies {

	object AndroidSupport {

		const val AndroidApplicationPlugin  =   "com.android.application"

		const val ActivityKtx               =   "androidx.activity:activity-ktx:${LibraryVersion.ACTIVITY}"
		const val AndroidGradlePlugin       =   "com.android.tools.build:gradle:${GradlePluginVersion.ANDROID_GRADLE}"

		const val AppCompat                 =   "androidx.appcompat:appcompat:${LibraryVersion.APPCOMPAT}"
		const val CoreKtx                   =   "androidx.core:core-ktx:${LibraryVersion.CORE_KTX}"
		const val CoreUtils                 =   "androidx.legacy:legacy-support-core-utils:${LibraryVersion.SUPPORT_CORE_UTILS}"
		const val PlayCore                  =   "com.google.android.play:core:${LibraryVersion.PLAY_CORE}"

		object Design {
			cosnt val Design                    =   "com.google.android.material:material:${LibraryVersion.MATERIAL}"
			cosnt val Recyclerview              =   "androidx.recyclerview:recyclerview:${LibraryVersion.RECYCLER_VIEW}"
			cosnt val RecyclerviewSelection     =   "androidx.recyclerview:recyclerview-selection:${LibraryVersion.RECYCLER_VIEW_SELECTION}"
			cosnt val ConstraintLayout          =   "androidx.constraintlayout:constraintlayout:${LibraryVersion.CONSTRAINT_LAYOUT}"
			cosnt val Cardview                  =   "androidx.cardview:cardview:${LibraryVersion.SUPPORT_CARD_VIEW}"
		}

		object Fragment {
			const val FragmentRuntimeKtx        =   "androidx.fragment:fragment-ktx:${LibraryVersion.FRAGMENT}"
			const val FragmentTesting           =   "androidx.fragment:fragment-testing:${LibraryVersion.FRAGMENT}"

		}

		object Gms {
			const val GoogleGmsPlugin           =   "com.google.gms.google-services"
			const val GoogleGms                 =   "com.google.gms:google-services:${LibraryVersion.GMS_GOOGLE_SERVICES}"
		}
	}

	object Facebook {
		const val Shimmer                   =   "com.facebook.shimmer:shimmer:${LibraryVersion.SHIMMER}"
	}

	object Firebase {
		const val Core                      =  "com.google.firebase:firebase-core:${LibraryVersion.FIREBASE_CORE}"
		const val Analytics                 =  "com.google.firebase:firebase-analytics:${LibraryVersion.FIREBASE_CORE}"
		const val Messaging                 =  "com.google.firebase:firebase-messaging:${LibraryVersion.FIREBASE_MESSAGING}"
		const val CommonKtx                 =  "com.google.firebase:firebase-common-ktx:${LibraryVersion.FIREBASE_KTX}"
		const val Crashlytics               =  "com.crashlytics.sdk.android:crashlytics:${LibraryVersion.FIREBASE_CRASHLYTICS}"
	}

	object Glide {
		const val Runtime                   =   "com.github.bumptech.glide:glide:${LibraryVersion.GLIDE}"
		const val Compiler                  =   "com.github.bumptech.glide:compiler:${LibraryVersion.GLIDE}"
	}

	object GoogleMaps {
		const val Location                  =   "com.google.android.gms:play-services-location:${LibraryVersion.G_MAPS_LOCATION}"
		const val MapsKtx                   =   "com.google.maps.android:maps-ktx:${LibraryVersion.G_MAPS_KTX}"
		const val MapsUtilsKtx              =   "com.google.maps.android:maps-utils-ktx:${LibraryVersion.G_MAPS_UTILS_KTX}"
		const val Places                    =   "com.google.android.libraries.places:places:${LibraryVersion.G_MAPS_PLACES}"
	}

	object Koin {
		const val Koin                       =  "org.koin:koin-android:${LibraryVersion.KOIN}"
		const val Scope                      =  "org.koin:koin-android-scope:${LibraryVersion.KOIN}"
		const val Viewmodel                  =  "org.koin:koin-android-viewmodel:${LibraryVersion.KOIN}"
		const val Test                       =  "org.koin:koin-test:${LibraryVersion.KOIN}"
		const val Gradleplugin               =  "org.koin:koin-gradle-plugin:${LibraryVersion.KOIN}"
	}
	
	object Kotlin {
		const val Core                      =   "org.jetbrains.kotlinx:kotlinx-coroutines-core:${CoreVersion.KOTLIN_CORE}"
		const val Reflection                =   "org.jetbrains.kotlin:kotlin-reflect:${CoreVersion.KOTLIN_REFLECTION}"
		object Coroutines {
			const val Core                  =   "org.jetbrains.kotlinx:kotlinx-coroutines-core:${CoreVersion.COROUTINES_ANDROID}"
			const val Android               =   "org.jetbrains.kotlinx:kotlinx-coroutines-android:${CoreVersion.COROUTINES_ANDROID}"
		}
	}
	
	object Lifecycle {
		const val Runtime                   =   "androidx.lifecycle:lifecycle-runtime:${LibraryVersion.LIFECYCLE}"
		const val Java8                     =   "androidx.lifecycle:lifecycle-common-java8:${LibraryVersion.LIFECYCLE}"
		const val Compiler                  =   "androidx.lifecycle:lifecycle-compiler:${LibraryVersion.LIFECYCLE}"
		const val ViewmodelKtx              =   "androidx.lifecycle:lifecycle-viewmodel-ktx:${LibraryVersion.LIFECYCLE}"
		const val LivedataKtx               =   "androidx.lifecycle:lifecycle-livedata-ktx:${LibraryVersion.LIFECYCLE}"
		const val LivedataExtensions        =   "androidx.lifecycle:lifecycle-extensions:${LibraryVersion.LIFECYCLE}"
		const val Coroutines                =   "androidx.lifecycle:lifecycle-livedata-ktx:${LibraryVersion.LIFECYCLE}"
	}

	object Main {
		const val Gson                      =   "com.google.code.gson:gson:${LibraryVersion.GSON}"
		const val Leakcanary                =   "com.squareup.leakcanary:leakcanary-android:${LibraryVersion.LEAKCANARY}"
		const val Timber                    =   "com.jakewharton.timber:timber:${LibraryVersion.TIMBER}"
	}

	object Navigation {
		const val  RuntimeKtx               =   "androidx.navigation:navigation-runtime-ktx:${CoreVersion.NAVIGATION}"
		const val  FragmentKtx              =   "androidx.navigation:navigation-fragment-ktx:${CoreVersion.NAVIGATION}"
		const val  UiKtx                    =   "androidx.navigation:navigation-ui-ktx:${CoreVersion.NAVIGATION}"
		const val  SafeArgsPlugin           =   "androidx.navigation:navigation-safe-args-gradle-plugin:${CoreVersion.NAVIGATION}"
		const val  SafeArgsApplyPlugin      =   "androidx.navigation.safeargs.kotlin"
	}

	object Okhttp {
		const val Main                      =   "com.squareup.okhttp3:okhttp:${LibraryVersion.OKHTTP_3}"
		const val LoggingInterceptor        =   "com.squareup.okhttp3:logging-interceptor:${LibraryVersion.OKHTTP_LOGGING_INTERCEPTOR}"
	}

	object Other {
		const val BtnWithCircleLoader       =   "br.com.simplepass:loading-button-android:${LibraryVersion.BTN_WITH_CIRCLE_LOADER}"
		const val CircleImageView           =   "de.hdodenhof:circleimageview:${LibraryVersion.CIRCLE_IMAGEVIEW}"
		const val FloatingSearchView        =   "com.github.arimorty:floatingsearchview:${LibraryVersion.FLOATING_SEARCH_VIEW}"
		const val MaterialCalendar          =   "com.applandeo:material-calendar-view:${LibraryVersion.MATERIAL_CALENDAR}"
		const val RoundedImageView          =   "com.makeramen:roundedimageview:${LibraryVersion.ROUNDED_IMAGEVIEW}"
	}

	object Retrofit {
		const val Runtime                   =   "com.squareup.retrofit2:retrofit:${LibraryVersion.RETROFIT}"
		const val Mock                      =   "com.squareup.retrofit2:retrofit-mock:${LibraryVersion.RETROFIT}"
		object Converters {
			const val Scalars               =   "com.squareup.retrofit2:converter-scalars:${LibraryVersion.RETROFIT}"
			const val Moshi                 =   "com.squareup.retrofit2:converter-moshi:${LibraryVersion.RETROFIT}"
			const val Gson                  =   "com.squareup.retrofit2:converter-gson:${LibraryVersion.RETROFIT}"
		}
	}
	
	object Room {
		const val Runtime                    =  "androidx.room:room-runtime:${LibraryVersion.ROOM}"
		const val Ktx                        =  "androidx.room:room-ktx:${LibraryVersion.ROOM}"
		const val Compiler                   =  "androidx.room:room-compiler:${LibraryVersion.ROOM}"
	}

	object WorkManager {
		const val Runtime                    =  "androidx.work:work-runtime:${LibraryVersion.WORK_MANAGER}"
		const val Testing                    =  "androidx.work:work-testing:${LibraryVersion.WORK_MANAGER}"
		const val Firebase                   =  "androidx.work:work-firebase:${LibraryVersion.WORK_MANAGER}"
		const val RuntimeKtx                 =  "androidx.work:work-runtime-ktx:${LibraryVersion.WORK_MANAGER}"
	}

}