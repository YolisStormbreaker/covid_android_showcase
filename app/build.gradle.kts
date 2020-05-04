plugins {
	id(GradlePluginId.ANDROID_APPLICATION)
	kotlin("android")
	kotlin("android.extensions")
	kotlin("kapt")
	id(GradlePluginId.GOOGLE_GMS_PLUGIN)
	id(GradlePluginId.CRASHLYTICS_PLUGIN)
	id(GradlePluginId.SAFE_ARGS)
	id(GradlePluginId.GRADLE_UPDATE_PLUGIN)
}

android {
	compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)
	buildToolsVersion(AndroidConfig.BUILD_TOOLS_VERSION)

	defaultConfig {
		applicationId = AndroidConfig.ID
		minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
		targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)
		versionCode = AndroidConfig.VERSION_CODE
		versionName = AndroidConfig.VERSION_NAME

		testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
	}

	buildTypes {
		getByName(BuildType.RELEASE) {
			isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
			proguardFiles("proguard-android.txt", "proguard-rules.pro")
		}

		getByName(BuildType.DEBUG) {
			isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
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

	api(project(ModuleDependency.LIBRARY_BASE))
	debugImplementation(LibraryDependencies.Main.Leakcanary)

	api(LibraryDependencies.AndroidSupport.Design.ConstraintLayout)
	api(LibraryDependencies.AndroidSupport.Design.Material)

	api(LibraryDependencies.Navigation.FragmentKtx)
	api(LibraryDependencies.Navigation.UiKtx)

}