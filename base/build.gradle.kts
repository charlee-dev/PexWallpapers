import commons.addComposeDependencies
import commons.addCoreDependencies
import dependencies.Dependencies

plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
}

val pex_base_url: String by project
val pex_api_access_key: String by project

android {
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes.forEach {
        it.buildConfigField("String", "PEX_BASE_URL", pex_base_url)
        it.buildConfigField("String", "PEX_API_ACCESS_KEY", pex_api_access_key)
    }
}

dependencies {

    implementation(project(Modules.CORE))

    addCoreDependencies()
    addComposeDependencies()

    api(Dependencies.timber)
}