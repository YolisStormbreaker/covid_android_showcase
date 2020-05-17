plugins {
    id(GradlePluginId.ANDROID_DYNAMIC_FEATURE)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_ANDROID_EXTENSIONS)
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
}

dependencies {
    implementation(project(ModuleDependency.app))
    implementation(project(ModuleDependency.DataSourceRepositoryCovidStats))

    addTestDependencies()
}
