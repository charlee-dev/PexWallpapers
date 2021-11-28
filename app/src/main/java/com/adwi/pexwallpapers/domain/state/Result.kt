package com.adwi.pexwallpapers.domain.state

sealed class Result(
    val message: String? = null
) {
    object Idle : Result()
    object Loading : Result()
    object Success : Result()
    class Error(message: String?) : Result(message)
}