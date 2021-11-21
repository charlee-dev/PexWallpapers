import commons.addCoreDependencies
import commons.addHiltDependenciesBasic
import dependencies.Dependencies

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
    api(project(Modules.REPOSITORY))

    addCoreDependencies()
    addHiltDependenciesBasic()

    implementation(Dependencies.composeNavigation)
    implementation(Dependencies.coil)
    implementation(Dependencies.paging)
    implementation(Dependencies.timber)

    implementation(Dependencies.composeWork)
    implementation(Dependencies.hiltWorkCore)
    kapt(Dependencies.hiltWorkCompiler)
}