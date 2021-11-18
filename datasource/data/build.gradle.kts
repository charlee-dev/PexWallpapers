import commons.addHiltDependenciesBasic
import commons.addNetworkDependencies
import commons.addPersistenceDependencies

plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.DAGGER_HILT)
    id(Plugins.ANDROID_JUNIT_5)
}

android {
    compileSdk = AndroidConfig.compileSdk
    defaultConfig {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
        testInstrumentationRunner = AndroidConfig.HILT_TEST_INSTRUMENTATION_RUNNER
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(Modules.COMMON))

    addPersistenceDependencies()
    addNetworkDependencies()
    addHiltDependenciesBasic()

//    addJUnit5()
//    implementation(TestDependencies.mockk)
//    implementation(TestDependencies.hilt)
//    implementation(TestDependencies.coroutinesTest)
////    implementation(TestDependencies.junit)
////    implementation(TestDependencies.kotest)
//    implementation(TestDependencies.truth_ext)
}