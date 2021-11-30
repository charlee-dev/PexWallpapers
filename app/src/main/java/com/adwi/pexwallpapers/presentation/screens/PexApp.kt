package com.adwi.pexwallpapers.presentation.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.PexBottomBar
import com.adwi.components.PexScaffold
import com.adwi.components.PexSnackBarHost
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.pexwallpapers.presentation.main.MainViewModel
import com.adwi.pexwallpapers.presentation.navigation.HomeSections
import com.adwi.pexwallpapers.presentation.navigation.MainDestinations
import com.adwi.pexwallpapers.presentation.navigation.myNavGraph
import com.adwi.pexwallpapers.presentation.navigation.rememberMyAppState
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