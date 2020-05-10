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

    buildFeatures.dataBinding = true
}

dependencies {

    api(LibraryDependencies.Main.Timber)
    implementation(LibraryDependencies.Koin.Main)
    implementation(LibraryDependencies.Firebase.Crashlytics)

}