package com.adwi.pexwallpapers.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.pexwallpapers.ui.components.PexBottomBar
import com.adwi.pexwallpapers.ui.components.PexScaffold
import com.adwi.pexwallpapers.ui.components.PexSnackBarHost
import com.adwi.pexwallpapers.ui.main.MainViewModel
import com.adwi.pexwallpapers.ui.navigation.HomeSections
import com.adwi.pexwallpapers.ui.navigation.MainDestinations
import com.adwi.pexwallpapers.ui.navigation.myNavGraph
import com.adwi.pexwallpapers.ui.navigation.rememberMyAppState
import com.adwi.pexwallpapers.ui.theme.PexWallpapersTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalPermissionsApi
@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@InternalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalCoilApi
@ExperimentalPagingApi
@ExperimentalFoundationApi
@Composable
fun PexApp(
    viewModel: MainViewModel,
) {
    ProvideWindowInsets {
        PexWallpapersTheme {

            val appState = rememberMyAppState()


            PexScaffold(
                viewModel = viewModel,
                bottomBar = {
                    if (appState.shouldShowBottomBar) {
                        PexBottomBar(
                            tabs = appState.bottomBarTabs,
                            currentRoute = appState.currentRoute!!,
                            navigateToRoute = appState::navigateToBottomBarRoute
                        )
                    }
                },
                snackbarHost = {
                    PexSnackBarHost(
                        hostState = it,
                        modifier = Modifier
                    )
                },
                scaffoldState = appState.scaffoldState
            ) {
                AnimatedNavHost(
                    navController = appState.navController,
                    startDestination = MainDestinations.HOME_ROUTE,
                    modifier = Modifier
                ) {
                    myNavGraph(
                        upPress = appState::upPress,
                        onWallpaperClick = appState::navigateToPreview,
                        onCategoryClick = appState::navigateToCategory,
                        navigateToSearch = { appState.navigateToBottomBarRoute(HomeSections.SEARCH.route) },
                        onAboutUsClick = {
                            TODO("add about us section")
                        },
                        onPrivacyPolicyClick = {
                            TODO("add privacy policy section")
                        },
                        onContactSupportClick = {
                            TODO("add  section")
                        }
                    )
                }
            }
        }
    }
}