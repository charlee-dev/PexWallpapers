import commons.*
import dependencies.Dependencies
import dependencies.TestDependencies

plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.DAGGER_HILT)
}

val pex_base_url: String by project
val pex_api_access_key: String by project

android {
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk

        testInstrumentationRunner = "com.adwi.data.HiltTestRunner"
    }

    buildTypes.forEach {
        it.buildConfigField("String", "PEX_BASE_URL", pex_base_url)
        it.buildConfigField("String", "PEX_API_ACCESS_KEY", pex_api_access_key)
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
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    api(project(Modules.CORE))
    api(project(Modules.DOMAIN))

    addHiltDependenciesBasic()
    addPersistenceDependencies()
    addNetworkDependencies()

    implementation(Dependencies.timber)
    implementation(Dependencies.paging)

    // Test
    androidTestImplementation(TestDependencies.coroutinesTest)
    androidTestImplementation(TestDependencies.truth)
    androidTestImplementation(TestDependencies.test_runner)
    androidTestImplementation(TestDependencies.test_core)

    testImplementation(TestDependencies.hiltTest)
    kaptTest(TestDependencies.hiltTestCompiler)

    androidTestImplementation(TestDependencies.hiltTest)
    kaptAndroidTest(TestDependencies.hiltTestCompiler)
}