import commons.addHiltDependenciesBasic
import commons.addNetworkDependencies
import commons.addPersistenceDependencies
import dependencies.TestDependencies

plugins {
    id(Plugins.ANDROID_APPLICATION)
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

dependencies {

    implementation(project(Modules.COMMON))

    addPersistenceDependencies()
    addNetworkDependencies()
    addHiltDependenciesBasic()

//    testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.junit5}")
//    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}")
//    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.junit5}")
//    testImplementation("com.github.thecodeside:timber-junit5-extension:${Versions.timber}")
//    testImplementation("io.mockk:mockk:${Versions.mockk}")
//    testImplementation("io.kotest:kotest-assertions-core:${Versions.kotest}")
//    testImplementation("app.cash.turbine:turbine:${Versions.turbine}")
//    implementation(TestDependencies.hilt)
    implementation(TestDependencies.coroutinesTest)
    implementation(TestDependencies.truth)
    implementation(TestDependencies.test_runner)
    implementation(TestDependencies.test_core)

    testImplementation("com.google.dagger:hilt-android-testing:2.38.1")
    kaptTest("com.google.dagger:hilt-android-compiler:2.38.1")

    androidTestImplementation("com.google.dagger:hilt-android-testing:2.38.1")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.38.1")

}