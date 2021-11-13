package com.adwi.preview

sealed class PreviewEvents {
    data class GetWallpaperById(val wallpaperId: Int) : PreviewEvents()
}
