@file:Suppress("UnstableApiUsage")



pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "PexWallpapers"

include(
    ":app",
    ":core",
    ":common",
    ":components",

    ":wallpapers_datasource",
    ":wallpapers_datasource:data",
    ":wallpapers_datasource:domain",
    ":wallpapers_datasource:repository",

    ":feature_settings",
)