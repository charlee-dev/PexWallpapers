package com.adwi.components.theme

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.adwi.components.R

data class MenuItem(
    @StringRes val name: Int,
    val icon: ImageVector
)

object MenuItems {
    val Search = MenuItem(
        name = R.string.search,
        icon = Icons.Outlined.Search
    )

    val GiveFeedback = MenuItem(
        name = R.string.give_feedback,
        icon = Icons.Outlined.Feedback
    )

    val RequestFeature = MenuItem(
        name = R.string.request_feature,
        icon = Icons.Outlined.Feedback
    )

    val ShowTips = MenuItem(
        name = R.string.show_tips,
        icon = Icons.Outlined.TipsAndUpdates
    )

    val ResetSettings = MenuItem(
        name = R.string.reset_settings,
        icon = Icons.Outlined.Restore
    )

    val DeleteAllFavorites = MenuItem(
        name = R.string.delete_all_favorites,
        icon = Icons.Outlined.Delete
    )

    val Refresh = MenuItem(
        name = R.string.refresh,
        icon = Icons.Outlined.Refresh
    )
}