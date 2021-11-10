import commons.addCoreDependencies
import commons.addHiltDependenciesBasic
import dependencies.Dependencies

plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)

    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.DAGGER_HILT)
}

val pex_base_url: String by project
val pex_api_access_key: String by project

android {
    compileSdk = AndroidConfig.compileSdk
    defaultConfig {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
    }

    buildTypes.forEach {
        it.buildConfigField("String", "PEX_BASE_URL", pex_base_url)
        it.buildConfigField("String", "PEX_API_ACCESS_KEY", pex_api_access_key)
    }
}

dependencies {
    api(project(Modules.DOMAIN))

    api(Dependencies.timber)
    addCoreDependencies()
    addHiltDependenciesBasic()

    api(Dependencies.coroutinesCore)
    implementation(Dependencies.lifecycle)
}