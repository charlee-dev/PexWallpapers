object AndroidConfig {
    const val id = "com.adwi.pexwallpapers"
    const val minSdk = 26
    const val compileSdk = 31
    const val targetSdk = 31
    const val versionCode = 1
    const val versionName = "0.1.0"
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
    const val HILT_TEST_INSTRUMENTATION_RUNNER = "com.adwi.pexwallpapers.HiltTestRunner"
}

interface BuildType {

    companion object {
        const val RELEASE = "release"
        const val DEBUG = "debug"
    }

    val isMinifyEnabled: Boolean
}

object BuildTypeDebug : BuildType {
    override val isMinifyEnabled = false
}

object BuildTypeRelease : BuildType {
    override val isMinifyEnabled = false
}

object TestOptions {
    const val IS_RETURN_DEFAULT_VALUES = true
}