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
//    composeOptions {
//        kotlinCompilerExtensionVersion = Versions.compose
//    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(Modules.COMMON))
    implementation(project(Modules.SHARED_WALLPAPER_SETTER))
    implementation(project(Modules.SHARED_IMAGE))
    api(project(Modules.REPOSITORY))

    addCoreDependencies()
    addHiltDependenciesBasic()

    implementation(Dependencies.coil)

    implementation(Dependencies.composeWork)
    implementation(Dependencies.hiltWorkCore)
    kapt(Dependencies.hiltWorkCompiler)
}