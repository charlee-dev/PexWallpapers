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
    ":components",
//    ":test-shared",

    ":pex",
    ":pex:domain",
    ":pex:datasource",
    ":pex:interactors",

    ":feature_home",
    ":feature_search",
    ":feature_favorites",
    ":feature_preview",
    ":feature_settings"
)