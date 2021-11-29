package com.adwi.pexwallpapers.domain.state

sealed class Resource(
    val message: String? = null
) {
    object Idle : Resource()
    data class Loading(val progress: Float? = null) : Resource()
    class Success(message: String? = null) : Resource(message)
    class Error(message: String? = null) : Resource(message)
}