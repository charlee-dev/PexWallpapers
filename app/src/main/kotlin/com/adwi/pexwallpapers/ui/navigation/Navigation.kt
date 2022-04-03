package com.adwi.pexwallpapers.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.*
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.base.BaseViewModel
import com.adwi.feature_favorites.ui.FavoritesScreen
import com.adwi.feature_favorites.ui.FavoritesViewModel
import com.adwi.feature_home.ui.HomeScreen
import com.adwi.feature_home.ui.HomeViewModel
import com.adwi.feature_preview.ui.PreviewScreen
import com.adwi.feature_preview.ui.PreviewViewModel
import com.adwi.feature_search.ui.SearchScreen
import com.adwi.feature_search.ui.SearchViewModel
import com.adwi.feature_settings.data.database.model.Settings
import com.adwi.feature_settings.ui.settings.SettingsScreen
import com.adwi.feature_settings.ui.settings.SettingsViewModel
import com.adwi.feature_settings.ui.privacy.PrivacyPolicyScreen
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
    upPress: () -> Unit,
    onAboutUsClick: (NavBackStackEntry) -> Unit,
    onPrivacyPolicyClick: (NavBackStackEntry) -> Unit,
    onContactSupportClick: () -> Unit,
    onSaveAutomationClick: () -> Unit,
    cancelWorks: () -> Unit,
    onShareClick: (Wallpaper) -> Unit,
    onDownloadClick: (Wallpaper) -> Unit,
    onGiveFeedbackClick: () -> Unit,
    onRequestFeature: () -> Unit,
    onReportBugClick: () -> Unit,
    onHomeClick: (url: String) -> Unit,
    onLockClick: (url: String) -> Unit,
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.HOME.route
    ) {
        addHomeGraph(
            settings = settings,
            onWallpaperClick = onWallpaperClick,
            onCategoryClick = onCategoryClick,
            onAboutUsClick = onAboutUsClick,
            onPrivacyPolicyClick = onPrivacyPolicyClick,
            onContactSupportClick = onContactSupportClick,
            onSaveAutomationClick = onSaveAutomationClick,
            cancelWorks = cancelWorks,
            onGiveFeedbackClick = onGiveFeedbackClick,
            onRequestFeature = onRequestFeature,
            onReportBugClick = onReportBugClick
        )
    }
    // PREVIEW
    composable(
        route = "${MainDestinations.WALLPAPER_PREVIEW_ROUTE}/{${MainDestinations.WALLPAPER_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.WALLPAPER_ID_KEY) {
            type = NavType.IntType
        })
    ) { backStackEntry ->
        val viewModel = hiltViewModel<PreviewViewModel>(backStackEntry)

        getCurrentSettings(viewModel, settings)

        PreviewScreen(
            viewModel = viewModel,
            upPress = upPress,
            onShareClick = onShareClick,
            onDownloadClick = onDownloadClick,
            onGiveFeedbackClick = onGiveFeedbackClick,
            onRequestFeature = onRequestFeature,
            onReportBugClick = onReportBugClick,
            onHomeClick = onHomeClick,
            onLockClick = onLockClick
        )
    }
    // SEARCH
    composable(
        route = "${MainDestinations.SEARCH_ROUTE}/{${MainDestinations.SEARCH_QUERY}}",
        arguments = listOf(navArgument(MainDestinations.SEARCH_QUERY) {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        val viewModel = hiltViewModel<SearchViewModel>(backStackEntry)

        viewModel.restoreSavedQuery()
        getCurrentSettings(viewModel, settings)

        SearchScreen(
            viewModel = viewModel,
            onWallpaperClick = { id -> onWallpaperClick(id, backStackEntry) },
            onGiveFeedbackClick = onGiveFeedbackClick,
            onRequestFeature = onRequestFeature,
            onReportBugClick = onReportBugClick
        )
    }
    composable(
        route = MainDestinations.PRIVACY_POLICY
    ) { backStackEntry ->
        val viewModel = hiltViewModel<SettingsViewModel>(backStackEntry)

        getCurrentSettings(viewModel, settings)

        PrivacyPolicyScreen(
            viewModel = viewModel,
            upPress = upPress,
            onGiveFeedbackClick = onGiveFeedbackClick,
            onRequestFeature = onRequestFeature,
            onReportBugClick = onReportBugClick
        )
    }
    composable(
        route = MainDestinations.ABOUT_US
    ) { backStackEntry ->
        val viewModel = hiltViewModel<SettingsViewModel>(backStackEntry)

        getCurrentSettings(viewModel, settings)

//        AboutUsScreen(
//            viewModel = viewModel,
//            upPress = upPress
//        ) TODO(add About us screen)
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
    onAboutUsClick: (NavBackStackEntry) -> Unit,
    onPrivacyPolicyClick: (NavBackStackEntry) -> Unit,
    onContactSupportClick: () -> Unit,
    onSaveAutomationClick: () -> Unit,
    cancelWorks: () -> Unit,
    onGiveFeedbackClick: () -> Unit,
    onRequestFeature: () -> Unit,
    onReportBugClick: () -> Unit
) {
    composable(HomeSections.HOME.route) { backStackEntry ->
        val viewModel = hiltViewModel<HomeViewModel>(backStackEntry)

        viewModel.onStart()
        getCurrentSettings(viewModel, settings)

        HomeScreen(
            viewModel = viewModel,
            onWallpaperClick = { id -> onWallpaperClick(id, backStackEntry) },
            onCategoryClick = { query -> onCategoryClick(query, backStackEntry) },
            onGiveFeedbackClick = onGiveFeedbackClick,
            onRequestFeature = onRequestFeature,
            onReportBugClick = onReportBugClick
        )
    }
    composable(HomeSections.SEARCH.route) { backStackEntry ->
        val viewModel = hiltViewModel<SearchViewModel>(backStackEntry)

        viewModel.restoreSavedQuery()
        getCurrentSettings(viewModel, settings)

        SearchScreen(
            viewModel = viewModel,
            onWallpaperClick = { id -> onWallpaperClick(id, backStackEntry) },
            onGiveFeedbackClick = onGiveFeedbackClick,
            onRequestFeature = onRequestFeature,
            onReportBugClick = onReportBugClick
        )
    }
    composable(HomeSections.FAVORITES.route) { backStackEntry ->
        val viewModel = hiltViewModel<FavoritesViewModel>(backStackEntry)

        viewModel.getFavorites()
        getCurrentSettings(viewModel, settings)

        FavoritesScreen(
            viewModel = viewModel,
            onWallpaperClick = { id -> onWallpaperClick(id, backStackEntry) },
            onGiveFeedbackClick = onGiveFeedbackClick,
            onRequestFeature = onRequestFeature,
            onReportBugClick = onReportBugClick
        )
    }
    composable(HomeSections.SETTINGS.route) { backStackEntry ->
        val viewModel = hiltViewModel<SettingsViewModel>(backStackEntry)

        viewModel.getSettings()

        SettingsScreen(
            viewModel = viewModel,
            onAboutUsClick = { onAboutUsClick(backStackEntry) },
            onPrivacyPolicyClick = { onPrivacyPolicyClick(backStackEntry) },
            onContactSupportClick = onContactSupportClick,
            onSaveAutomationClick = onSaveAutomationClick,
            cancelWorks = cancelWorks,
            onGiveFeedbackClick = onGiveFeedbackClick,
            onRequestFeature = onRequestFeature,
            onReportBugClick = onReportBugClick
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

private fun getCurrentSettings(viewModel: BaseViewModel, settings: Settings) {
    viewModel.lowRes = settings.lowResMiniatures
    viewModel.showShadows = settings.showShadows
    viewModel.showParallax = settings.showParallax
}