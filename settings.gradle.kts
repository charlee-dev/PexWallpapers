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
    ":base",
    ":components",
    ":core",
    ":data",
    ":domain",
    ":test_utils",

    ":feature_home",
    ":feature_search",
    ":feature_favorites",
    ":feature_settings",
    ":feature_preview",

    ":feature_image",
    ":feature_automation",
)