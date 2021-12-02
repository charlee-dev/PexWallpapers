import commons.*
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

    implementation(project(Modules.CORE))
    implementation(project(Modules.DOMAIN))
    implementation(project(Modules.DATA))
    implementation(project(Modules.BASE))
    implementation(project(Modules.COMPONENTS))

    addDefaultComposeDependencies()
    addComposeDependencies()
    addHiltDependenciesBasic()

    implementation(Dependencies.timber)
    implementation(Dependencies.coil)

    // Test
    implementation(TestDependencies.coroutinesTest)
    implementation(TestDependencies.truth)
    implementation(TestDependencies.test_runner)
    implementation(TestDependencies.test_core)
    implementation(TestDependencies.turbine)

    testImplementation(TestDependencies.hiltTest)
    kaptTest(TestDependencies.hiltTestCompiler)

    androidTestImplementation(TestDependencies.hiltTest)
    kaptAndroidTest(TestDependencies.hiltTestCompiler)
}