import commons.addHiltDependenciesBasic
import commons.addNetworkDependencies
import commons.addPersistenceDependencies
import dependencies.TestDependencies

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
        testInstrumentationRunner = "com.adwi.datasource.HiltTestRunner"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

hilt {
    enableExperimentalClasspathAggregation = true
}

dependencies {

    implementation(project(Modules.COMMON))

    addPersistenceDependencies()
    addNetworkDependencies()
    addHiltDependenciesBasic()

    implementation(TestDependencies.coroutinesTest)
    implementation(TestDependencies.truth)
    implementation(TestDependencies.test_runner)
    implementation(TestDependencies.test_core)

    testImplementation("com.google.dagger:hilt-android-testing:2.38.1")
    kaptTest("com.google.dagger:hilt-android-compiler:2.38.1")

    androidTestImplementation("com.google.dagger:hilt-android-testing:2.38.1")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.38.1")

}