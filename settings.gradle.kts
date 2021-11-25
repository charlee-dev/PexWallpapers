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
    ":common",
    ":components",
    ":shared",

    ":datasource:domain",
    ":datasource:data",
    ":datasource:repository",

    ":datasource_settings",

    ":feature_home",
    ":feature_search",
    ":feature_favorites",
    ":feature_preview",
    ":feature_settings"
)