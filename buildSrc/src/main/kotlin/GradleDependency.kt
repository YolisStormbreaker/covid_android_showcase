object GradlePluginVersion {
    const val ANDROID_GRADLE                =   "4.0.0-beta05"
    const val GRADLE_VERSION_PLUGIN         =   "0.22.0"
    const val KOTLIN                        =   CoreVersion.KOTLIN
    const val SAFE_ARGS                     =   CoreVersion.NAVIGATION
}

object GradlePluginId {
    const val ANDROID_APPLICATION           =   "com.android.application"
    const val ANDROID_DYNAMIC_FEATURE       =   "com.android.dynamic-feature"
    const val ANDROID_LIBRARY               =   "com.android.library"
    const val KOIN_GRADLE_PLUGIN            =   "org.koin:koin-gradle-plugin:${CoreVersion.KOIN}"
    const val KOTLIN_JVM                    =   "org.jetbrains.kotlin.jvm"
    const val KOTLIN_ANDROID                =   "org.jetbrains.kotlin.android"
    const val KOTLIN_ANDROID_EXTENSIONS     =   "org.jetbrains.kotlin.android.extensions"
    const val KOTLIN_GRADLE_PLUGIN          =   "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN"
    const val GOOGLE_GMS_PLUGIN             =   "com.google.gms.google-services"
    const val SAFE_ARGS                     =   "androidx.navigation.safeargs.kotlin"
}

object GradleOldWayPlugins {
    const val ANDROID_GRADLE                =   "com.android.tools.build:gradle:${GradlePluginVersion.ANDROID_GRADLE}"
    const val SAFE_ARGS                     =   "androidx.navigation:navigation-safe-args-gradle-plugin:${GradlePluginVersion.SAFE_ARGS}"
}