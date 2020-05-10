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


tasks {
	// Gradle versions plugin configuration
	"dependencyUpdates"(com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask::class) {
		resolutionStrategy {
			componentSelection {
				all {
					// Do not show pre-release version of library in generated dependency report
					val rejected = kotlin.collections.listOf(
						"alpha",
						"beta",
						"rc",
						"cr",
						"m",
						"preview"
					)
						.map { qualifier -> kotlin.text.Regex("(?i).*[.-]$qualifier[.\\d-]*") }
						.any { it.matches(candidate.version) }
					if (rejected) {
						reject("Release candidate")
					}

					// kAndroid newest version is 0.8.8 (jcenter), however maven repository contains version 0.8.7 and
					// plugin fails to recognize it correctly
					if (candidate.group == "com.pawegio.kandroid") {
						reject("version ${candidate.version} is broken for ${candidate.group}'")
					}
				}
			}
		}
	}
}

task<Delete>("clean"){
	delete(rootProject.buildDir)
}