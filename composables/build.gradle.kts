import commons.addComposeDependencies
import commons.addCoreDependencies
import dependencies.Dependencies

plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
}

android {
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
}

dependencies {

    implementation(project(Modules.CORE))

    addCoreDependencies()
    addComposeDependencies()

    implementation(Dependencies.accompanistCoil)
    implementation(Dependencies.shimmer)
    implementation(Dependencies.lottie)
    implementation(Dependencies.accompanistPager)
    implementation(Dependencies.accompanistPagerIndicators)
    implementation(Dependencies.accompanistPlaceholder)
}