// Top-level build file where you can add configuration options common to all sub-projects/modules.
    buildscript {
        ext.kotlin_version = "1.3.72"
        repositories {
            google()
            jcenter()
        }
        dependencies {
            classpath "com.android.tools.build:gradle:4.0.0-beta05"
            classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"

            // NOTE: Do not place your application dependencies here; they belong
            // in the individual module build.gradle.kts files
        }
    }

    allprojects {
        repositories {
            google()
            jcenter()
        }
    }

    task clean (type: Delete) {
        delete rootProject.buildDir
    }