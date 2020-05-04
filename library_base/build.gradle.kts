plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_ANDROID_EXTENSIONS)
}

android {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)
    buildToolsVersion(AndroidConfig.BUILD_TOOLS_VERSION)

    defaultConfig {
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
    api(LibraryDependencies.Kotlin.Core)
    api(LibraryDependencies.Kotlin.Reflection)
    api(LibraryDependencies.Kotlin.Coroutines.Android)

    api(LibraryDependencies.Main.Timber)
    api(LibraryDependencies.AndroidSupport.AppCompat)
    api(LibraryDependencies.AndroidSupport.CoreKtx)
    api(LibraryDependencies.AndroidSupport.Fragment.FragmentRuntimeKtx)
    api(LibraryDependencies.Lifecycle.Extensions)
    api(LibraryDependencies.Lifecycle.LivedataKtx)
    api(LibraryDependencies.Lifecycle.ViewModelKtx)
    api(LibraryDependencies.Firebase.Crashlytics)
    api(LibraryDependencies.Firebase.CommonKtx)
}