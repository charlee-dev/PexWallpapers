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
    const val composeAnimationCore = "androidx.compose.animation:animation-core:${Versions.compose}"
    const val composeRuntime = "androidx.compose.runtime:runtime:${Versions.compose}"
    const val composeMaterial3 = "androidx.compose.material3:material3:${Versions.material3}"
    const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.navigation}"
    const val composeActivity = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val composeWork = "androidx.work:work-runtime-ktx:${Versions.work}"
    const val composePaging = "androidx.paging:paging-compose:${Versions.pagingCompose}"

    // Network
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val okHttp = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val sandwich = "com.github.skydoves:sandwich:${Versions.sandwich}"

    // Persistence
    const val room = "androidx.room:room-ktx:${Versions.room}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomPaging = "androidx.paging:paging-common-ktx:${Versions.paging}"

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
    const val paging = "androidx.paging:paging-common-ktx:${Versions.paging}"
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
    const val accompanistNavigationAnimation =
        "com.google.accompanist:accompanist-navigation-animation:${Versions.accompanist}"
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
    const val kotest = "io.kotest:kotest-assertions-core:${Versions.kotest}"


    // JUnit5
    const val jupiterApi = "org.junit.jupiter:junit-jupiter-api:${Versions.junit5}"
    const val jupiterEngineRuntimeOnly = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit5}"
    const val jupiterParams = "org.junit.jupiter:junit-jupiter-params:${Versions.junit5}"
    const val jupiterVintageRuntimeOnly =
        "org.junit.vintage:junit-vintage-engine:${Versions.junit5}"

    const val kotlin_junit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    const val test_runner = "androidx.test:runner:${Versions.test_core}"

    const val hilt = "com.google.dagger:hilt-android-testing:${Versions.hilt}"

    const val mockk = "io.mockk:mockk:${Versions.mockk}"

    const val truth = "com.google.truth:truth:${Versions.truth}"
    const val rules = "androidx.test:rules:${Versions.rules}"
}