package commons

import dependencies.Dependencies
import dependencies.TestDependencies
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.addTestDependencies() {

    testImplementation(TestDependencies.test_core)
    testImplementation(TestDependencies.arch_core)
    testImplementation(TestDependencies.espresso_core)
    testImplementation(TestDependencies.turbine)
    testImplementation(TestDependencies.kotest)
    testImplementation(TestDependencies.truth)
//    testImplementation(TestDependencies.jupiterApi)
//    testImplementation(TestDependencies.jupiterEngineRuntimeOnly)
//    testImplementation(TestDependencies.mockk)
}

fun DependencyHandler.addCoreDependencies() {
    implementation(Dependencies.appcompat)
    implementation(Dependencies.coreKtx)
}

fun DependencyHandler.addDefaultComposeDependencies() {
    api(Dependencies.composeCompiler)
    api(Dependencies.composeActivity)
    api(Dependencies.composeUiUtil)
    api(Dependencies.composeMaterial)
//    api(Dependencies.composeMaterial3)
    api(Dependencies.composePreview)
    api(Dependencies.lifecycleRuntime)
}

fun DependencyHandler.addComposeDependencies() {
    addDefaultComposeDependencies()
    api(Dependencies.composeUi)
    api(Dependencies.composeMaterialIcons)
    api(Dependencies.composeTooling)
    api(Dependencies.composeFoundation)
    api(Dependencies.composeFoundationLayout)
    api(Dependencies.composeAnimation)
    api(Dependencies.composeAnimationCore)
    api(Dependencies.composeRuntime)
    api(Dependencies.composeNavigation)
    api(Dependencies.composePaging)
}

fun DependencyHandler.addHelpersDependencies() {
    api(Dependencies.accompanistCoil)
    api(Dependencies.accompanistInsets)
    api(Dependencies.shimmer)
    api(Dependencies.lottie)
    api(Dependencies.accompanistPager)
    api(Dependencies.accompanistPagerIndicators)
    api(Dependencies.accompanistPlaceholder)
    api(Dependencies.accompanistNavigationAnimation)
    api(Dependencies.accompanistSwipeRefresh)
}

fun DependencyHandler.addNetworkDependencies() {
    api(Dependencies.retrofit)
    implementation(Dependencies.gson)
    implementation(Dependencies.okHttp)
    implementation(Dependencies.sandwich)
}

fun DependencyHandler.addPersistenceDependencies() {
    api(Dependencies.room)
    implementation(Dependencies.roomRuntime)
    kapt(Dependencies.roomCompiler)
    implementation(Dependencies.roomPaging)
}

fun DependencyHandler.addHiltDependenciesBasic() {
    implementation(Dependencies.hiltCore)
    kapt(Dependencies.hiltCompiler)
}

fun DependencyHandler.addHiltDependenciesExtended() {
    implementation(Dependencies.hiltLifecycleViewModel)
    implementation(Dependencies.hiltNavigationCompose)
}

fun DependencyHandler.addJUnit5() {
    testImplementation(TestDependencies.jupiterApi)
    testRuntimeOnly(TestDependencies.jupiterEngineRuntimeOnly)
    testImplementation(TestDependencies.jupiterParams)
    testRuntimeOnly(TestDependencies.jupiterVintageRuntimeOnly)
    testRuntimeOnly(TestDependencies.kotlin_junit)
}

/*
 * These extensions mimic the extensions that are generated on the fly by Gradle.
 * They are used here to provide above dependency syntax that mimics Gradle Kotlin DSL
 * syntax in module\build.gradle.kts files.
 */
@Suppress("detekt.UnusedPrivateMember")
fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

@Suppress("detekt.UnusedPrivateMember")
fun DependencyHandler.api(dependencyNotation: Any): Dependency? =
    add("api", dependencyNotation)

@Suppress("detekt.UnusedPrivateMember")
fun DependencyHandler.kapt(dependencyNotation: Any): Dependency? =
    add("kapt", dependencyNotation)

@Suppress("detekt.UnusedPrivateMember")
fun DependencyHandler.kaptTest(dependencyNotation: Any): Dependency? =
    add("kaptTest", dependencyNotation)

@Suppress("detekt.UnusedPrivateMember")
fun DependencyHandler.kaptAndroidTest(dependencyNotation: Any): Dependency? =
    add("kaptAndroidTest", dependencyNotation)

fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

private fun DependencyHandler.testRuntimeOnly(dependencyNotation: Any): Dependency? =
    add("testRuntimeOnly", dependencyNotation)

fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)

@Suppress("unchecked_cast", "nothing_to_inline", "detekt.UnsafeCast")
private inline fun <T> uncheckedCast(obj: Any?): T = obj as T