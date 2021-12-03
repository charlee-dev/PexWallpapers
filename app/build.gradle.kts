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

        testInstrumentationRunner = AndroidConfig.ROBOELECTRIC_TEST_RUNNER

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
    implementation(project(Modules.BASE))
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
    implementation(Dependencies.timber)

    implementation(Dependencies.workManager)
    implementation(Dependencies.hiltWorkCore)
    kapt(Dependencies.hiltWorkCompiler)

    implementation(Dependencies.accompanistPermissions)

    // Test
    implementation(TestDependencies.test_runner)
    implementation(TestDependencies.test_core)

    testImplementation(TestDependencies.junit4)
    implementation(TestDependencies.truth)
    implementation(TestDependencies.GoogleTruth)

//    testImplementation(TestDependencies.junit4Ktx)
//    testImplementation(TestDependencies.test_core)

//    implementation(TestDependencies.coroutinesTest)

//    testImplementation(TestDependencies.hiltTest)
//    kaptTest(TestDependencies.hiltTestCompiler)

//    androidTestImplementation(TestDependencies.hiltTest)
//    kaptAndroidTest(TestDependencies.hiltTestCompiler)

    testImplementation("org.robolectric:robolectric:4.6")

    implementation(TestDependencies.work)
}