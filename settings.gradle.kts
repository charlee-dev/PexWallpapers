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
    ":base",
    ":components",

    ":wallpapers_datasource",
    ":wallpapers_datasource:data",
    ":wallpapers_datasource:domain",

    ":feature_home",
    ":feature_search",
    ":feature_favorites",
    ":feature_settings",
    ":feature_preview"
)