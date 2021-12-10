import dependencies.Versions

object Build {
    const val gradle = "com.android.tools.build:gradle:${Versions.gradleBuildTool}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val jUnit5 = "de.mannodermaus.gradle.plugins:android-junit5:${Versions.junit5Plugin}"
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
}