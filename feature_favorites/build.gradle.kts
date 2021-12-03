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
            pickFirsts.add("META-INF/licenses/ASM")
            pickFirsts.add("win32-x86/attach_hotspot_windows.dll")
            pickFirsts.add("win32-x86-64/attach_hotspot_windows.dll")
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//            excludes += "**/attach_hotspot_windows.dll"
        }
    }
}

dependencies {


    implementation(project(Modules.DATA))
    implementation(project(Modules.BASE))
    implementation(project(Modules.COMPONENTS))
    implementation(project(Modules.TEST_UTILS))

    addDefaultComposeDependencies()
    addComposeDependencies()
    addHiltDependenciesBasic()

    implementation(Dependencies.timber)
    implementation(Dependencies.coil)

    // Test
    testImplementation(TestDependencies.coroutinesTest) {
        // conflicts with mockk due to direct inclusion of byte buddy
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-debug")
    }
    testImplementation(TestDependencies.truth)
    testImplementation(TestDependencies.test_runner)
    testImplementation(TestDependencies.test_core)
    testImplementation(TestDependencies.turbine)
    testImplementation(TestDependencies.mockk)

    testImplementation(TestDependencies.hiltTest)
    kaptTest(TestDependencies.hiltTestCompiler)

    androidTestImplementation(TestDependencies.hiltTest)
    kaptAndroidTest(TestDependencies.hiltTestCompiler)
}