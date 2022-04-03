package com.adwi.pexwallpapers.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val WALLPAPER_PREVIEW_ROUTE = "wallpaper_preview"
    const val WALLPAPER_ID_KEY = "wallpaperId"
    const val SEARCH_ROUTE = "search_route"
    const val SEARCH_QUERY = "query"
    const val PRIVACY_POLICY = "privacy_policy"
    const val ABOUT_US = "about_us"
}

@ExperimentalAnimationApi
@Composable
fun rememberMyAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberAnimatedNavController()
) =
    remember(scaffoldState, navController) {
        PexAppState(scaffoldState, navController)
    }

@Stable
class PexAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
) {
    val bottomBarTabs = HomeSections.values()
    private val bottomBarRoutes = bottomBarTabs.map { it.route }

    val shouldShowBottomBar: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes

    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun upPress() {
        navController.navigateUp()
    }

    fun navigateToBottomBarRoute(route: String) {
        if (route != currentRoute) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                popUpTo(findStartDestination(navController.graph).id) {
                    saveState = true
                }
            }
        }
    }

    fun navigateToPreview(wallpaperId: Int, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.WALLPAPER_PREVIEW_ROUTE}/$wallpaperId")
        }
    }

    fun navigateToCategory(categoryName: String, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.SEARCH_ROUTE}/$categoryName")
        }
    }

    fun navigateToPrivacyPolicy(from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate(MainDestinations.PRIVACY_POLICY)
        }
    }
}