
plugins {
	id(GradlePluginId.GRADLE_VERSION_PLUGIN)
	id(GradlePluginId.KOTLIN_JVM) apply false
	id(GradlePluginId.KOTLIN_ANDROID) apply false
	id(GradlePluginId.KOTLIN_ANDROID_EXTENSIONS) apply false
	id(GradlePluginId.ANDROID_APPLICATION) apply false
	id(GradlePluginId.ANDROID_DYNAMIC_FEATURE) apply false
	id(GradlePluginId.ANDROID_LIBRARY) apply false
	id(GradlePluginId.SAFE_ARGS) apply false
}

allprojects {
	repositories {
		google()
		jcenter()
	}
}

tasks {
	// Gradle versions plugin configuration
	"dependencyUpdates"(DependencyUpdatesTask::class) {
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
