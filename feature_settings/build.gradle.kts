import commons.addCoreDependencies
import commons.addDefaultComposeDependencies
import commons.addHiltDependenciesBasic
import commons.addTestDependencies

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
            pickFirsts += "META-INF/atomicfu.kotlin_module"
            pickFirsts += "win32-x86/attach_hotspot_windows.dll"
            pickFirsts += "win32-x86-64/attach_hotspot_windows.dll"
            pickFirsts += "META-INF/licenses/ASM"
            excludes += "**/attach_hotspot_windows.dll"
            excludes += "META-INF/licenses/**"
            excludes += "META-INF/AL2.0"
            excludes += "META-INF/LGPL2.1"
        }
    }
}

dependencies {

    implementation(project(Modules.COMMON))
    implementation(project(Modules.COMPONENTS))
    implementation(project(Modules.SHARED))
    api(project(Modules.REPOSITORY))

    addCoreDependencies()
    addHiltDependenciesBasic()
    addDefaultComposeDependencies()
    addTestDependencies()
}