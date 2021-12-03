package com.adrianwitaszak.tool_image

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.adrianwitaszak.tool_image.util.*
import com.adwi.core.DataState
import com.adwi.core.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.File
import java.io.IOException
import javax.inject.Inject

class ImageManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val wallpaperManager: WallpaperManager
) : ImageManager {

    override fun saveWallpaperLocally(wallpaperId: Int, bitmap: Bitmap): Uri? {
        val file = getFileByWallpaperId(wallpaperId.toString())
        file.compressStream(bitmap)
        Timber.tag(TAG).d("Backing up image to local")
        return file.getUri()
    }

    // Images saved in sdcard/Pictures/pex_wallpapers/
    @SuppressLint("InlinedApi")
    override suspend fun saveWallpaperToGallery(wallpaperId: Int, bitmap: Bitmap): Uri? {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, Constants.MIME_TYPE)
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Constants.DIR_RELATIVE_PATH)
        values.put(MediaStore.Images.Media.IS_PENDING, true)
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "${Constants.DISPLAY_NAME}$wallpaperId")

        val uri: Uri? =
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        return if (uri != null) {
            bitmap.saveToStream(context, uri)
            values.put(MediaStore.Images.Media.IS_PENDING, false)
            context.contentResolver.update(uri, values, null, null)
            uri
        } else null
    }


    override suspend fun getBitmapFromRemote(imageUrl: String): DataState<Bitmap?> {
        return try {
            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(imageUrl)
                .allowHardware(false) // Disable hardware bitmaps.
                .build()

            val drawable = (loader.execute(request) as SuccessResult).drawable
            val bitmap = (drawable as BitmapDrawable).bitmap
            DataState.Success(bitmap)
        } catch (e: Throwable) {
            Timber.tag(TAG).d(e.localizedMessage)
            DataState.Error(e)
        }
    }

    override fun deleteBackupBitmap(wallpaperId: String) {
        val file = getFileByWallpaperId(wallpaperId)
        file.delete()
        Timber.tag(TAG).d("Deleted image $wallpaperId")
    }

    override fun deleteAllBackups() {
        val directory = ContextWrapper(context).getDir(Constants.DIR_IMAGES, Context.MODE_PRIVATE)
        directory.let {
            directory.deleteRecursively()
            Timber.tag(TAG).d("Deleted all backups")
        }
    }

    override fun restoreBackup(wallpaperId: String): Bitmap? {
        val file = getFileByWallpaperId(wallpaperId)
        Timber.tag(TAG).d("Restoring backup")
        return file.decodeBitmap()
    }

    override fun getFileByWallpaperId(wallpaperId: String): File {
        val directory = ContextWrapper(context).getDir(Constants.DIR_IMAGES, Context.MODE_PRIVATE)
        val fileName = "${Constants.BACKUP_WALLPAPER}$wallpaperId${Constants.IMAGE_FILE_EXT}"
        return File(directory, fileName)
    }

    override fun setWallpaper(bitmap: Bitmap, home: Boolean, lock: Boolean): Resource {
        return try {
            if (home) {
                wallpaperManager.setBitmap(bitmap)
            }
            if (lock) {
                wallpaperManager.setBitmap(
                    bitmap, null, true, WallpaperManager.FLAG_LOCK
                )
            }
            Resource.Success()
        } catch (ex: IOException) {
            Timber.tag(TAG).d("Exception: ${ex.printStackTrace()}")
            Resource.Error(message = ex.localizedMessage)
        }
    }

    @SuppressLint("MissingPermission")
    override fun getCurrentWallpaper(): DataState<Bitmap> {
        return try {
            val currentWallpaper = wallpaperManager
                .drawable
                .toBitmap()
            DataState.Success(currentWallpaper)
        } catch (t: Throwable) {
            DataState.Error(t)
        }
    }
}

private const val TAG = "ImageTools"