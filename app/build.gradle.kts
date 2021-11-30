import commons.*
import dependencies.Dependencies
import dependencies.TestDependencies
import org.gradle.kotlin.dsl.implementation
import org.gradle.kotlin.dsl.kapt

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

        testInstrumentationRunner = "com.adwi.pexwallpapers.HiltTestRunner"

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
    implementation(project(Modules.CORE))
    implementation(project(Modules.COMMON))
    implementation(project(Modules.DOMAIN))
    implementation(project(Modules.REPOSITORY))
    implementation(project(Modules.DATA))

    implementation(project(Modules.FEATURE_HOME))
    implementation(project(Modules.FEATURE_SEARCH))
    implementation(project(Modules.FEATURE_FAVORITES))
    implementation(project(Modules.FEATURE_SETTINGS))
    implementation(project(Modules.FEATURE_PREVIEW))

    addComposeDependencies()
    addHelpersDependencies()
    addHiltDependenciesBasic()
    addHiltDependenciesExtended()

    implementation(Dependencies.accompanistNavigationAnimation)
    implementation(Dependencies.timber)

    implementation(Dependencies.workManager)
    implementation(Dependencies.hiltWorkCore)
    kapt(Dependencies.hiltWorkCompiler)

    implementation(Dependencies.accompanistPermissions)

    // Test
    implementation(TestDependencies.coroutinesTest)
    implementation(TestDependencies.truth)
    implementation(TestDependencies.test_runner)
    implementation(TestDependencies.test_core)

    testImplementation(TestDependencies.hiltTest)
    kaptTest(TestDependencies.hiltTestCompiler)

    androidTestImplementation(TestDependencies.hiltTest)
    kaptAndroidTest(TestDependencies.hiltTestCompiler)
}