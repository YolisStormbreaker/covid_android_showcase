plugins {
	id(GradlePluginId.ANDROID_APPLICATION)
	kotlin("android")
	kotlin("android.extensions")
	id(GradlePluginId.DETEKT)
	id(GradlePluginId.GOOGLE_GMS_PLUGIN)
	id(GradlePluginId.CRASHLYTICS_PLUGIN)
	id(GradlePluginId.SAFE_ARGS)
	id(GradlePluginId.GRADLE_UPDATE_PLUGIN)
}

android {
	compileSdkVersion(AndroidDefaultConfig.COMPILE_SDK_VERSION)
	buildToolsVersion(AndroidDefaultConfig.BUILD_TOOLS_VERSION)

	defaultConfig {
		applicationId = AndroidDefaultConfig.ID
		minSdkVersion(AndroidDefaultConfig.MIN_SDK_VERSION)
		targetSdkVersion(AndroidDefaultConfig.TARGET_SDK_VERSION)
		versionCode = AndroidDefaultConfig.VERSION_CODE
		versionName = AndroidDefaultConfig.VERSION_NAME

		testInstrumentationRunner = AndroidDefaultConfig.TEST_INSTRUMENTATION_RUNNER

		buildConfigField("FEATURE_MODULE_NAMES", getDynamicFeatureModuleNames())

	}
	flavorDimensions("version")
	productFlavors {
		create(FullPulse.flavorName) {
			applicationIdSuffix  = FullPulse.applicationIdSuffix
			versionCode = FullPulse.versionCode
			versionNameSuffix  = FullPulse.versionNameSuffix
		}
	}
	buildTypes {
		getByName(BuildType.RELEASE) {
			isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
			proguardFiles("proguard-android.txt", "proguard-rules.pro")
			manifestPlaceholders = mapOf("enableCrashReporting" to "true", "enableFirebaseAnalyticsReporting" to "true")
		}

		getByName(BuildType.DEBUG) {
			isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
			manifestPlaceholders = mapOf("enableCrashReporting" to "false", "enableFirebaseAnalyticsReporting" to "false")
		}

		compileOptions {
			sourceCompatibility = JavaVersion.VERSION_1_8
			targetCompatibility = JavaVersion.VERSION_1_8
		}

		testOptions {
			unitTests.isReturnDefaultValues = TestOptions.IS_RETURN_DEFAULT_VALUES
		}
	}

	// Each feature module that is included in settings.gradle.kts is added here as dynamic feature
	dynamicFeatures = ModuleDependency.getDynamicFeatureModules().toMutableSet()

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}

	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_1_8.toString()
	}

	buildFeatures.dataBinding = true

}

dependencies {

	api(project(ModuleDependency.LibraryCommon))
	api(project(ModuleDependency.LibraryFirebaseAnalytics))
	debugImplementation(LibraryDependencies.Main.Leakcanary)

	implementation(LibraryDependencies.Koin.Core)
	implementation(LibraryDependencies.Koin.Ext)

	api(LibraryDependencies.AndroidSupport.Design.ConstraintLayout)
	api(LibraryDependencies.AndroidSupport.Design.Material)

	api(LibraryDependencies.Navigation.FragmentKtx)
	api(LibraryDependencies.Navigation.UiKtx)
	api(LibraryDependencies.Navigation.DynamicFeature)

	addTestDependencies()
}

fun com.android.build.gradle.internal.dsl.BaseFlavor.buildConfigFieldFromGradleProperty(gradlePropertyName: String) {
	val propertyValue = project.properties[gradlePropertyName] as? String
	checkNotNull(propertyValue) { "Gradle property $gradlePropertyName is null" }

	val androidResourceName = "GRADLE_${gradlePropertyName.toSnakeCase()}".toUpperCase()
	buildConfigField("String", androidResourceName, propertyValue)
}

fun getDynamicFeatureModuleNames() = ModuleDependency.getDynamicFeatureModules()
	.map { it.replace(":feature:", "") }
	.toSet()

fun String.toSnakeCase() = this.split(Regex("(?=[A-Z])")).joinToString("_") { it.toLowerCase() }

fun com.android.build.gradle.internal.dsl.DefaultConfig.buildConfigField(name: String, value: Set<String>) {
	// Generates String that holds Java String Array code
	val strValue = value.joinToString(prefix = "{", separator = ",", postfix = "}", transform = { "\"$it\"" })
	buildConfigField("String[]", name, strValue)
}