plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
}

android {
    compileSdk = AndroidConfig.compileSdk
    defaultConfig {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
    }
}

dependencies {
    api(project(Modules.DOMAIN))
}