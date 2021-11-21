import commons.addHiltDependenciesBasic
import commons.addHiltDependenciesExtended
import dependencies.Dependencies

plugins {
    id(Plugins.ANDROID_APPLICATION)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.DAGGER_HILT)
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

    implementation(project(Modules.COMPONENTS))

    implementation(project(Modules.FEATURE_HOME))
    implementation(project(Modules.FEATURE_PREVIEW))
    implementation(project(Modules.FEATURE_SEARCH))
    implementation(project(Modules.FEATURE_FAVORITES))
    implementation(project(Modules.FEATURE_SETTINGS))

    implementation(project(Modules.SHARED_WORK))
    implementation(project(Modules.SHARED_NOTIFICATIONS))

    addHiltDependenciesBasic()
    addHiltDependenciesExtended()

    implementation(Dependencies.accompanistNavigationAnimation)

//    implementation(Dependencies.hiltWorkCore)
//    kapt(Dependencies.hiltWorkCompiler)
}
