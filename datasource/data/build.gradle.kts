import commons.addHiltDependenciesBasic
import commons.addNetworkDependencies
import commons.addPersistenceDependencies

plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.DAGGER_HILT)
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

    implementation(project(Modules.COMMON))

    addPersistenceDependencies()
    addNetworkDependencies()
    addHiltDependenciesBasic()
//    addTestDependencies()

//    implementation(Dependencies.paging)
//    implementation(Dependencies.composePaging)
}