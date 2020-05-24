plugins {
	id(GradlePluginId.ANDROID_DYNAMIC_FEATURE)
	id(GradlePluginId.KOTLIN_ANDROID)
	id(GradlePluginId.KOTLIN_ANDROID_EXTENSIONS)
	kotlin("kapt")
	id(GradlePluginId.SAFE_ARGS)
}

android {
	compileSdkVersion(AndroidDefaultConfig.COMPILE_SDK_VERSION)

	defaultConfig {
		minSdkVersion(AndroidDefaultConfig.MIN_SDK_VERSION)
		targetSdkVersion(AndroidDefaultConfig.TARGET_SDK_VERSION)

		versionCode = AndroidDefaultConfig.VERSION_CODE
		versionName = AndroidDefaultConfig.VERSION_NAME
		testInstrumentationRunner = AndroidDefaultConfig.TEST_INSTRUMENTATION_RUNNER
	}
	buildTypes {
		getByName(BuildType.RELEASE) {
			proguardFiles("proguard-android.txt", "proguard-rules.pro")
		}

		getByName(BuildType.DEBUG) {
		}
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}

	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_1_8.toString()
	}

	// This "test" source set is a fix for SafeArgs classes not being available when running Unit tests from cmd
	// See: https://issuetracker.google.com/issues/139242292
	sourceSets {
		getByName("test").java.srcDir("${project.rootDir}/app/build/generated/source/navigation-args/debug")
	}

	// Removes the need to mock need to mock classes that may be irrelevant from test perspective
	testOptions {
		unitTests.isReturnDefaultValues = TestOptions.IS_RETURN_DEFAULT_VALUES
	}

	buildFeatures.dataBinding = true
}

dependencies {
	implementation(project(ModuleDependency.app))
	implementation(project(ModuleDependency.DataSourceRepositoryCovidStats))

	implementation(LibraryDependencies.Koin.Core)
	implementation(LibraryDependencies.Koin.Ext)
	implementation(LibraryDependencies.Koin.ViewModel)
	implementation(LibraryDependencies.Koin.Scope)

	implementation(LibraryDependencies.AndroidSupport.Design.Material)
	implementation(LibraryDependencies.AndroidSupport.Design.SwipeToRefreshLayout)

	addTestDependencies()
}
