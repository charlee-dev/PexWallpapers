import commons.*
import dependencies.Dependencies
import org.gradle.kotlin.dsl.implementation

plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
    id(Plugins.SQL_DELIGHT)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.DAGGER_HILT)
    kotlin("plugin.serialization") version "1.5.31"
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

    api(project(Modules.base))

    addNetworkDependencies()
    addPersistenceDependencies()
    addHiltDependenciesBasic()
    addTestDependencies()

    implementation(Dependencies.coroutinesCore)
}