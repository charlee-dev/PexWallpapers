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
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
    }
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs = listOf(
            *kotlinOptions.freeCompilerArgs.toTypedArray(),
            "-Xallow-jvm-ir-dependencies",
            "-Xskip-prerelease-check",
            "-Xopt-in=androidx.paging.ExperimentalPagingApi",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }
}