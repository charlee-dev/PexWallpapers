package com.adwi.pexwallpapers.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.*
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.feature_favorites.FavoritesScreen
import com.adwi.feature_favorites.FavoritesViewModel
import com.adwi.feature_home.HomeScreen
import com.adwi.feature_home.HomeViewModel
import com.adwi.feature_preview.presentation.PreviewScreen
import com.adwi.feature_preview.presentation.PreviewViewModel
import com.adwi.feature_search.presentation.SearchScreen
import com.adwi.feature_search.presentation.SearchViewModel
import com.adwi.feature_settings.data.database.model.Settings
import com.adwi.feature_settings.presentation.SettingsScreen
import com.adwi.feature_settings.presentation.SettingsViewModel
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalPermissionsApi
@InternalCoroutinesApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalCoilApi
@ExperimentalPagingApi
@ExperimentalFoundationApi
fun NavGraphBuilder.myNavGraph(
    settings: Settings,
    onWallpaperClick: (Int, NavBackStackEntry) -> Unit,
    onCategoryClick: (String, NavBackStackEntry) -> Unit,
    navigateToSearch: () -> Unit,
    upPress: () -> Unit,
    onAboutUsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onContactSupportClick: () -> Unit,
    onSaveAutomationClick: () -> Unit,
    cancelWorks: () -> Unit,
    onShareClick: (Wallpaper) -> Unit,
    onDownloadClick: (Wallpaper) -> Unit,
    onSetWallpaperClick: (url: String, home: Boolean, lock: Boolean) -> Unit
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.HOME.route
    ) {
        addHomeGraph(
            settings = settings,
            onWallpaperClick = onWallpaperClick,
            onCategoryClick = onCategoryClick,
            navigateToSearch = navigateToSearch,
            onAboutUsClick = onAboutUsClick,
            onPrivacyPolicyClick = onPrivacyPolicyClick,
            onContactSupportClick = onContactSupportClick,
            onSaveAutomationClick = onSaveAutomationClick,
            cancelWorks = cancelWorks
        )
    }

    composable(
        route = "${MainDestinations.WALLPAPER_PREVIEW_ROUTE}/{${MainDestinations.WALLPAPER_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.WALLPAPER_ID_KEY) {
            type = NavType.IntType
        })
    ) { backStackEntry ->
        val viewModel = hiltViewModel<PreviewViewModel>(backStackEntry)
        PreviewScreen(
            viewModel = viewModel,
            upPress = upPress,
            onShareClick = onShareClick,
            onDownloadClick = onDownloadClick,
            onSetWallpaperClick = onSetWallpaperClick
        )
    }

    composable(
        route = "${MainDestinations.SEARCH_ROUTE}/{${MainDestinations.SEARCH_QUERY}}",
        arguments = listOf(navArgument(MainDestinations.SEARCH_QUERY) {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        val viewModel = hiltViewModel<SearchViewModel>(backStackEntry)

        viewModel.restoreSavedQuery()

        SearchScreen(
            viewModel = viewModel,
            onWallpaperClick = { id -> onWallpaperClick(id, backStackEntry) }
        )
    }
}

@ExperimentalPermissionsApi
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalCoilApi
@ExperimentalPagingApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
fun NavGraphBuilder.addHomeGraph(
    settings: Settings,
    onWallpaperClick: (Int, NavBackStackEntry) -> Unit,
    onCategoryClick: (String, NavBackStackEntry) -> Unit,
    navigateToSearch: () -> Unit,
    onAboutUsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onContactSupportClick: () -> Unit,
    onSaveAutomationClick: () -> Unit,
    cancelWorks: () -> Unit
) {
    composable(HomeSections.HOME.route) { backStackEntry ->
        val viewModel = hiltViewModel<HomeViewModel>(backStackEntry)

        viewModel.onStart()
        viewModel.lowRes = settings.lowResMiniatures

        HomeScreen(
            viewModel = viewModel,
            onWallpaperClick = { id -> onWallpaperClick(id, backStackEntry) },
            onCategoryClick = { query -> onCategoryClick(query, backStackEntry) },
            navigateToSearch = navigateToSearch
        )
    }
    composable(HomeSections.SEARCH.route) { backStackEntry ->
        val viewModel = hiltViewModel<SearchViewModel>(backStackEntry)

        viewModel.restoreSavedQuery()
        viewModel.lowRes = settings.lowResMiniatures

        SearchScreen(
            viewModel = viewModel,
            onWallpaperClick = { id -> onWallpaperClick(id, backStackEntry) }
        )
    }
    composable(HomeSections.FAVORITES.route) { backStackEntry ->
        val viewModel = hiltViewModel<FavoritesViewModel>(backStackEntry)

        viewModel.getFavorites()
        viewModel.lowRes = settings.lowResMiniatures

        FavoritesScreen(
            viewModel = viewModel,
            onSearchClick = navigateToSearch,
            onWallpaperClick = { id -> onWallpaperClick(id, backStackEntry) }
        )
    }
    composable(HomeSections.SETTINGS.route) { backStackEntry ->
        val viewModel = hiltViewModel<SettingsViewModel>(backStackEntry)

        viewModel.getSettings()

        SettingsScreen(
            viewModel = viewModel,
            onAboutUsClick = onAboutUsClick,
            onPrivacyPolicyClick = onPrivacyPolicyClick,
            onContactSupportClick = onContactSupportClick,
            onSaveAutomationClick = onSaveAutomationClick,
            cancelWorks = cancelWorks
        )
    }
}

fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}