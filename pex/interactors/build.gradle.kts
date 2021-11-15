import commons.addHiltDependenciesBasic

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
    kotlinOptions {
        freeCompilerArgs + "-Xopt-in=androidx.paging.ExperimentalPagingApi"
        freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
    }
}

dependencies {

    implementation(project(Modules.COMMON))
    api(project(Modules.DATASOURCE))
    implementation("androidx.paging:paging-common-ktx:3.0.1")

    addHiltDependenciesBasic()
}