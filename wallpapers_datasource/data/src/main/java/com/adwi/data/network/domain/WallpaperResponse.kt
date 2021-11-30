package com.adwi.data.network.domain

import com.google.gson.annotations.SerializedName

data class WallpaperResponse(
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("photos")
    val wallpaperList: List<WallpaperDto>,
    @SerializedName("next_page")
    val nextPage: String
)