package com.adrianwitaszak.tool_image.wallpapersetter

import android.graphics.Bitmap
import com.adwi.core.Resource

interface WallpaperSetter {

    fun setWallpaper(bitmap: Bitmap, home: Boolean, lock: Boolean)

    fun getHomeScreenWallpaper(): Bitmap
    fun getLockScreenWallpaper(): Bitmap
}