package com.adwi.pexwallpapers.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.UriHandler

fun openUri(
    modifier: Modifier = Modifier,
    uriHandler: UriHandler,
    uri: String,
) {
    uriHandler.openUri(uri)
}