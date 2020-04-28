object AndroidConfig {
    const val COMPILE_SDK_VERSION = 29
    const val MIN_SDK_VERSION = 21
    const val TARGET_SDK_VERSION = 29
    const val BUILD_TOOLS_VERSION = "29.0.0"

    const val VERSION_CODE = 1
    const val VERSION_NAME = "0.0.1"

    const val ID = "com.yolisstorm.covid_pulse"
    const val TEST_INSTRUMENTATION_RUNNER = "android.support.test.runner.AndroidJUnitRunner"
}

interface BuildType {

    companion object {
        const val RELEASE = "release"
        const val DEBUG = "debug"
    }

    val isMinifyEnabled: Boolean
}

object BuildTypeDebug : BuildType {
    override val isMinifyEnabled = true
}

object BuildTypeRelease : BuildType {
    override val isMinifyEnabled = true
}

object TestOptions {
    const val IS_RETURN_DEFAULT_VALUES = true
}
