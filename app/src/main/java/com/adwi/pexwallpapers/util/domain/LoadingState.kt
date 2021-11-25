package com.adwi.pexwallpapers.util.domain

sealed class LoadingState {
    object Loading : LoadingState()
    object Idle : LoadingState()
}
