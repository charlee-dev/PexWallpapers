package com.adrianwitaszak.tool_image.wallpapersetter

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.graphics.drawable.toBitmap
import javax.inject.Inject


class WallpaperSetterImpl @Inject constructor(
    private val wallpaperManager: WallpaperManager
) : WallpaperSetter {

    override fun setWallpaper(bitmap: Bitmap, home: Boolean, lock: Boolean) {
        if (home) {
            wallpaperManager.setBitmap(bitmap)
        }
        if (lock) {
            wallpaperManager.setBitmap(
                bitmap, null, true, WallpaperManager.FLAG_LOCK
            )
        }
    }

    @SuppressLint("MissingPermission")
    override fun getHomeScreenWallpaper(): Bitmap =
        wallpaperManager
            .drawable
            .toBitmap()

    @SuppressLint("MissingPermission")
    override fun getLockScreenWallpaper(): Bitmap {
        val parcel = wallpaperManager.getWallpaperFile(WallpaperManager.FLAG_SYSTEM)
        val bitmap = BitmapFactory.decodeFileDescriptor(parcel.fileDescriptor)
        parcel.close()
        return bitmap
    }
}