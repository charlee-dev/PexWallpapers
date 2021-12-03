package com.adwi.tool_automation

import android.graphics.Bitmap
import android.net.Uri
import com.adrianwitaszak.tool_image.ImageManager
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

    override suspend fun getBitmapFromRemote(imageUrl: String): DataState<Bitmap?> =
        DataState.Success(bitmap)

    override fun getFileByWallpaperId(wallpaperId: String): File {
        TODO("Not yet implemented")
    }

    override fun restoreBackup(wallpaperId: String): Bitmap? {
        TODO("Not yet implemented")
    }

    override fun deleteBackupBitmap(wallpaperId: String) {
        TODO("Not yet implemented")
    }

    override fun deleteAllBackups() {
        TODO("Not yet implemented")
    }

    override fun setWallpaper(bitmap: Bitmap, home: Boolean, lock: Boolean): Resource {
        TODO("Not yet implemented")
    }

    override fun getCurrentWallpaper(): DataState<Bitmap> {
        TODO("Not yet implemented")
    }
}