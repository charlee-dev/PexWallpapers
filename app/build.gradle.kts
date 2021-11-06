import commons.addCoreDependencies
import commons.addDefaultComposeDependencies
import commons.addHiltDependenciesBasic
import commons.kapt
import dependencies.Dependencies

plugins {
    id(Plugins.ANDROID_APPLICATION)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.DAGGER_HILT)
//    id("kotlin-kapt")
//    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        applicationId = AndroidConfig.id
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
        versionCode = AndroidConfig.versionCode
        versionName = AndroidConfig.versionName

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    api(project(Modules.data))

    addCoreDependencies()
    addDefaultComposeDependencies()
    addHiltDependenciesBasic()

//    implementation("androidx.core:core-ktx:1.7.0")
//    implementation("androidx.compose.ui:ui:${Versions.compose}")
//    implementation("androidx.compose.material3:material3:1.0.0-alpha01")
//    implementation("androidx.compose.ui:ui-tooling-preview:${Versions.compose}")
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
//    implementation("androidx.activity:activity-compose:1.4.0")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.3")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${Versions.compose}")
//    debugImplementation("androidx.compose.ui:ui-tooling:${Versions.compose}")
//    debugImplementation("androidx.compose.ui:ui-test-manifest:${Versions.compose}")
}