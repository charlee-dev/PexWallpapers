import commons.addHiltDependenciesBasic
import commons.addNetworkDependencies
import commons.addPersistenceDependencies
import commons.addTestDependencies
import dependencies.Dependencies

plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    kotlin(Plugins.SERIALIZATION) version Versions.kotlin
    id(Plugins.DAGGER_HILT)
    id(Plugins.SQL_DELIGHT)
}

android {
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }
}

dependencies {

    implementation(project(Modules.base))
    implementation(project(Modules.domain))

    addNetworkDependencies()
    addPersistenceDependencies()
    addHiltDependenciesBasic()
    addTestDependencies()

    implementation(Dependencies.coroutinesCore)
}