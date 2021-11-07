buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
    }
    dependencies {
        classpath(Build.gradle)
        classpath(Build.kotlin)
        classpath(Build.jUnit5)
        classpath(Build.hilt)
        classpath(Build.sqlDelight)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}