package com.adrianwitaszak.tool_image.imagemanager

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

interface ImageManager {

    fun getBackup(wallpaperId: String): Bitmap?

    fun deleteBackup(wallpaperId: String)

    fun deleteAllBackups()

    suspend fun saveWallpaperToGallery(wallpaperId: Int, bitmap: Bitmap): Uri?

    fun saveWallpaperLocally(wallpaperId: Int, bitmap: Bitmap): Uri?

    suspend fun getBitmapFromRemote(imageUrl: String): Bitmap?

    fun getFileByWallpaperId(wallpaperId: String): File
}