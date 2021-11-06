buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradleBuildTool}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:${Versions.junit5Plugin}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}")
        classpath("com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}