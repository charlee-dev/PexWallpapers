package com.adwi.core.domain

sealed class LoadingState {

    object Loading : LoadingState()
    object Idle : LoadingState()
}