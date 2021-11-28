package com.adwi.pexwallpapers.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.adwi.pexwallpapers.R

enum class HomeSections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    HOME(R.string.home, Icons.Outlined.Home, "home/home"),
    SEARCH(R.string.search, Icons.Outlined.Search, "home/search"),
    FAVORITES(R.string.favorites, Icons.Outlined.Favorite, "home/favorites"),
    SETTINGS(R.string.settings, Icons.Outlined.Settings, "home/settings")
}