package com.adwi.pexwallpapers.model.state

sealed class Result(
    val message: String? = null
) {
    object Idle : Result()
    object Loading : Result()
    object Success : Result()
    class Error(message: String?) : Result(message)
}