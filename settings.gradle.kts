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
    ":test-shared",

    ":pex",
    ":pex:domain",
    ":pex:data",
    ":pex:usecases",

    ":features",
    ":features:base",
)