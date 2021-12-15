package com.adrianwitaszak.tool_image.wallpapersetter

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.graphics.drawable.toBitmap
import javax.inject.Inject

private const val tag = "WallpaperSetter"

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
    override fun getCurrentWallpaper(home: Boolean): Bitmap {
        return if (home) {
            wallpaperManager
                .drawable
                .toBitmap()
        } else {
            val parcel = wallpaperManager.getWallpaperFile(WallpaperManager.FLAG_SYSTEM)
            val bitmap = BitmapFactory.decodeFileDescriptor(parcel.fileDescriptor)
            parcel.close()
            bitmap
        }
    }
}