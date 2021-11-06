package com.adwi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WallpaperResponse(
    val page: Int,
    @SerialName("per_page")
    val perPage: Int,
    @SerialName("photos")
    val wallpaperList: List<WallpaperDto>,
    @SerialName("next_page")
    val nextPage: String
)