import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
	id(GradlePluginId.ANDROID_LIBRARY)
	id(GradlePluginId.KOTLIN_ANDROID)
	id(GradlePluginId.KOTLIN_ANDROID_EXTENSIONS)
}

android {
	compileSdkVersion(AndroidDefaultConfig.COMPILE_SDK_VERSION)
	buildToolsVersion(AndroidDefaultConfig.BUILD_TOOLS_VERSION)

	defaultConfig {
		minSdkVersion(AndroidDefaultConfig.MIN_SDK_VERSION)
		targetSdkVersion(AndroidDefaultConfig.TARGET_SDK_VERSION)
		versionCode = AndroidDefaultConfig.VERSION_CODE
		versionName = AndroidDefaultConfig.VERSION_NAME

		testInstrumentationRunner = AndroidDefaultConfig.TEST_INSTRUMENTATION_RUNNER

		buildConfigFieldFromGradleProperty("apiHeaderUrlTitle")
		buildConfigFieldFromGradleProperty("apiBaseUrl")
		buildConfigFieldFromGradleProperty("apiHeaderTokenTitle")
		buildConfigFieldFromGradleProperty("apiToken")
		buildConfigFieldFromGradleProperty("isNeedCommonLog", "Boolean")
		buildConfigFieldFromGradleProperty("isNeedOkHttpLog", "Boolean")


	}

	buildTypes {
		getByName(BuildType.RELEASE) {
			isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
			proguardFiles("proguard-android.txt", "proguard-rules.pro")
		}

		getByName(BuildType.DEBUG) {
			isDebuggable = true
			isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
		}

		testOptions {
			unitTests.isReturnDefaultValues = TestOptions.IS_RETURN_DEFAULT_VALUES
		}

		compileOptions {
			sourceCompatibility = JavaVersion.VERSION_1_8
			targetCompatibility = JavaVersion.VERSION_1_8
		}
	}



	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}

	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_1_8.toString()
	}
}

dependencies {

	implementation(LibraryDependencies.Firebase.Crashlytics)
	implementation(project(ModuleDependency.LibraryCommon))

	api(LibraryDependencies.Main.Timber)

	api(LibraryDependencies.Kotlin.Coroutines.Android)
	api(LibraryDependencies.AndroidSupport.CoreKtx)

	implementation(LibraryDependencies.Koin.Core)

	implementation(LibraryDependencies.Main.Gson)
	implementation(LibraryDependencies.Okhttp.Main)
	implementation(LibraryDependencies.Okhttp.LoggingInterceptor)
	implementation(LibraryDependencies.Retrofit.Runtime)
	implementation(LibraryDependencies.Retrofit.Converters.Gson)
	implementation(LibraryDependencies.Retrofit.Coroutine)

	addTestDependencies()

}


fun String.toSnakeCase() = this.split(Regex("(?=[A-Z])")).joinToString("_") { it.toLowerCase() }

fun com.android.build.gradle.internal.dsl.BaseFlavor.buildConfigFieldFromGradleProperty(
	gradlePropertyName: String,
	type: String = "String"
) {
	val propertyValue =
		project.properties[gradlePropertyName] as? String ?: gradleLocalProperties(projectDir)
			.getProperty(gradlePropertyName) as? String
	checkNotNull(propertyValue) { "Gradle property $gradlePropertyName is null" }

	val androidResourceName = "GRADLE_${gradlePropertyName.toSnakeCase()}".toUpperCase()
	buildConfigField(type, androidResourceName, propertyValue)
}