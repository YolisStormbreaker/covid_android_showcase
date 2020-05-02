
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// all projects = root project + sub projects
allprojects {
    repositories {
        google()
        jcenter()
    }
}

// JVM target applied to all Kotlin tasks across all sub-projects
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}