import commons.addHiltDependenciesBasic
import commons.addNetworkDependencies
import commons.addTestDependencies
import dependencies.Dependencies

plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
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

    implementation(project(Modules.BASE))
    implementation(project(Modules.DOMAIN))

    addNetworkDependencies()
    addHiltDependenciesBasic()
    addTestDependencies()

    implementation(Dependencies.paging)
    implementation(Dependencies.composePaging)
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.sqlDelightRuntime)
}

sqldelight {
    database("WallpaperDb") {
        packageName = "com.adwi.data.cache"
        sourceFolders = listOf("sqldelight")
    }
}