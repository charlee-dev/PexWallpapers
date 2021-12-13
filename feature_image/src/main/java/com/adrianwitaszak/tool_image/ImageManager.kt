package com.adrianwitaszak.tool_image

import android.graphics.Bitmap
import android.net.Uri
import com.adwi.core.DataState
import com.adwi.core.Resource
import java.io.File

interface ImageManager {

    fun saveWallpaperLocally(wallpaperId: Int, bitmap: Bitmap): Uri?

    suspend fun saveWallpaperToGallery(wallpaperId: Int, bitmap: Bitmap): Uri?

    suspend fun getBitmapFromRemote(imageUrl: String): DataState<Bitmap?>

    fun getFileByWallpaperId(wallpaperId: String): File

    fun restoreBackup(wallpaperId: String): Bitmap?

    fun deleteBackupBitmap(wallpaperId: String)

    fun deleteAllBackups()

    fun setWallpaper(bitmap: Bitmap, home: Boolean, lock: Boolean): Resource

    fun getCurrentWallpaper(home: Boolean): Bitmap
}