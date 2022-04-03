import commons.addHiltDependenciesBasic
import commons.implementation
import dependencies.Dependencies
import dependencies.TestDependencies

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

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = AndroidConfig.javaVersionName
        targetCompatibility = AndroidConfig.javaVersionName
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(Modules.CORE))

    addHiltDependenciesBasic()
    implementation(Dependencies.timber)
    implementation(Dependencies.coil)
    implementation(Dependencies.coreKtx)

    // Test
    androidTestImplementation(TestDependencies.test_runner)
    androidTestImplementation(TestDependencies.junit4Ktx)
    androidTestImplementation(TestDependencies.junit4)
    androidTestImplementation(TestDependencies.espresso_core)
    androidTestImplementation(TestDependencies.truth)
    androidTestImplementation(TestDependencies.GoogleTruth)
    androidTestImplementation(TestDependencies.mockito)
}