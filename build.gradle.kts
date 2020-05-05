buildscript {
	val kotlin_version by extra("1.3.72")
	addRepos(repositories)
	dependencies {
		classpath(GradleOldWayPlugins.ANDROID_GRADLE)
		classpath(kotlin("gradle-plugin", version = CoreVersion.KOTLIN))
		classpath(kotlin("serialization", version = CoreVersion.KOTLIN))
		classpath(GradleOldWayPlugins.GOOGLE_GMS_SERVICES)
		classpath(GradleOldWayPlugins.SAFE_ARGS)
		classpath(GradleOldWayPlugins.FIREBASE_CRASHLYTICS)
	}
}

plugins {
	id("com.github.ben-manes.versions") version GradlePluginVersion.GRADLE_UPDATER_VERSION_PLUGIN
}

allprojects {
	addRepos(repositories)
}

task<Delete>("clean"){
	delete(rootProject.buildDir)
}