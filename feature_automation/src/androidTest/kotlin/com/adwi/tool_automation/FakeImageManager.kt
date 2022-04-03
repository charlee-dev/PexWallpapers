package com.adwi.tool_automation

import android.graphics.Bitmap
import android.net.Uri
import com.adrianwitaszak.tool_image.imagemanager.ImageManager
import com.adwi.core.DataState
import com.adwi.core.Resource
import java.io.File

class FakeImageManager : ImageManager {

    val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)

    override fun saveWallpaperLocally(wallpaperId: Int, bitmap: Bitmap): Uri? {
        TODO("Not yet implemented")
    }

    override suspend fun saveWallpaperToGallery(wallpaperId: Int, bitmap: Bitmap): Uri? {
        TODO("Not yet implemented")
    }

    override suspend fun getBitmapFromRemote(imageUrl: String): Bitmap? =
        bitmap

    override fun getFileByWallpaperId(wallpaperId: String): File {
        TODO("Not yet implemented")
    }

    override fun getBackup(wallpaperId: String): Bitmap? {
        TODO("Not yet implemented")
    }

    override fun deleteBackup(wallpaperId: String) {
        TODO("Not yet implemented")
    }

    override fun deleteAllBackups() {
        TODO("Not yet implemented")
    }
}