import dependencies.Dependencies
import dependencies.TestDependencies
import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
}

val javaVersion: JavaVersion by extra { VERSION_1_8 }

android {
    compileSdkVersion(AndroidConfig.compileSdk)
    defaultConfig {
        minSdkVersion(AndroidConfig.minSdk)
        targetSdkVersion(AndroidConfig.targetSdk)
    }
    compileOptions {
        sourceCompatibility = VERSION_1_8
        targetCompatibility = VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    // Removes the need to mock need to mock classes that may be irrelevant from test perspective
    testOptions {
        unitTests.isReturnDefaultValues = TestOptions.IS_RETURN_DEFAULT_VALUES
    }
}

dependencies {
    implementation(Dependencies.kotlin)
    implementation(Dependencies.coroutinesAndroid)

    implementation(Dependencies.lifecycleRuntime)

    implementation(TestDependencies.junit)
    implementation(TestDependencies.rules)
    implementation(TestDependencies.turbine)
    api(TestDependencies.coroutinesTest)
}