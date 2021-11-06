package dependencies

object Dependencies {
    // Gradle
    const val buildTools = "com.android.tools.build:gradle:${Versions.gradleBuildTool}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

    // Core
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.core}"
    const val material = "com.google.android.material:material:${Versions.material}"

    // Coroutines
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    // Lifecycle
    const val lifecycle = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val lifecycleSavedState =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"
    const val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"

    // Compose
    const val composeCompiler = "androidx.compose.compiler:compiler:${Versions.compose}"
    const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    const val composeMaterialIcons =
        "androidx.compose.material:material-icons-extended:${Versions.compose}"
    const val composeUiUtil = "androidx.compose.ui:ui-util:${Versions.compose}"
    const val composePreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    const val composeTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val composeFoundation = "androidx.compose.foundation:foundation:${Versions.compose}"
    const val composeFoundationLayout =
        "androidx.compose.foundation:foundation-layout:${Versions.compose}"
    const val composeAnimation = "androidx.compose.animation:animation:${Versions.compose}"
    const val composeRuntime = "androidx.compose.runtime:runtime:${Versions.compose}"
    const val composeMaterial3 = "androidx.compose.material3:material3:${Versions.material3}"
    const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.navigation}"
    const val composeActivity = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val composeWork = "androidx.work:work-runtime-ktx:${Versions.work}"
    const val composePaging = "androidx.paging:paging-compose:${Versions.paging}"

    // Network
    const val ktorCore = "io.ktor:ktor-client-core:${Versions.ktor}"
    const val ktorClientSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
    const val ktorAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"
    const val ktorLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
    const val serialization =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"
    const val ktorClientMock = "io.ktor:ktor-client-mock:${Versions.ktor}"

    // Persistence
    const val sqlDelightRuntime = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"
    const val sqlDelightAndroidDriver =
        "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"

    // Hilt
    const val hiltCore = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltLifecycleViewModel =
        "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltNavigation}"
    const val hiltNavigationCompose =
        "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigation}"
    const val hiltWorkCore = "androidx.hilt:hilt-work:${Versions.hiltWork}"
    const val hiltWorkCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltWork}"

    // Helpers
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val lottie = "com.airbnb.android:lottie-compose:${Versions.lottie}"
    const val shimmer = "com.valentinilk.shimmer:compose-shimmer:${Versions.shimmer}"
    const val accompanistCoil =
        "com.google.accompanist:accompanist-coil:${Versions.accompanistCoil}"
    const val accompanistInsets =
        "com.google.accompanist:accompanist-insets:${Versions.accompanist}"
    const val accompanistPager = "com.google.accompanist:accompanist-pager:${Versions.accompanist}"
    const val accompanistPagerIndicators =
        "com.google.accompanist:accompanist-pager-indicators:${Versions.accompanist}"
    const val accompanistPlaceholder =
        "com.google.accompanist:accompanist-placeholder-material:${Versions.accompanist}"
    const val accompanistSwipeRefresh =
        "com.google.accompanist:accompanist-swiperefresh:${Versions.accompanist}"
}

object TestDependencies {
    // Core library
    const val test_core = "androidx.test:core:${Versions.test_core}"
    const val arch_core = "androidx.arch.core:core-testing:${Versions.arch_core}"

    // Espresso dependencies
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"

    // Coroutines
    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"

    // Assertions
    const val junit = "androidx.test.ext:junit:${Versions.junit}"
    const val rules = "androidx.test:rules:${Versions.rules}"
    const val kotest = "io.kotest:kotest-assertions-core:${Versions.kotest}"
    const val truth_ext = "androidx.test.ext:truth:${Versions.truth}"
    const val jupiter = "org.junit.jupiter:junit-jupiter-api:${Versions.junit5}"
    const val jupiterEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit5}"

    // Third party
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
}