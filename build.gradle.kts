// Top-level build file where you can add configuration options common to all sub-projects/modules.


buildscript {
    repositories.addRepos()
    dependencies {
        classpath(GradleOldWayPlugins.ANDROID_GRADLE)
        classpath(kotlin("gradle-plugin", version = CoreVersion.KOTLIN))
        classpath(kotlin("serialization", version = CoreVersion.KOTLIN))
        classpath(GradleOldWayPlugins.GOOGLE_GMS_SERVICES)
        classpath(GradleOldWayPlugins.SAFE_ARGS)
        classpath(GradleOldWayPlugins.FIREBASE_CRASHLYTICS)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id("com.github.ben-manes.versions") version "0.20.0"
}



allprojects {
    repositories.addRepos()
}


task<Delete>("clean"){
    delete(rootProject.buildDir)
}