package com.adrianwitaszak.tool_image.imagemanager

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.adrianwitaszak.tool_image.util.*
import com.adrianwitaszak.tool_image.util.Constants.DIR_RELATIVE_PATH
import com.adrianwitaszak.tool_image.util.compressStream
import com.adrianwitaszak.tool_image.util.getUri
import com.adrianwitaszak.tool_image.util.saveToStream
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class ImageManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ImageManager {

    private val tag = javaClass.name

    override fun saveWallpaperLocally(wallpaperId: Int, bitmap: Bitmap): Uri? {
        val file = getFileByWallpaperId(wallpaperId.toString())
        file.compressStream(bitmap)
        Timber.tag(tag).d("Backing up image to local")
        return file.getUri()
    }

    // Images saved in sdcard/Pictures/pex_wallpapers/
    @SuppressLint("InlinedApi")
    override suspend fun saveWallpaperToGallery(wallpaperId: Int, bitmap: Bitmap): Uri? {

        val values = ContentValues().apply {
            put(MediaStore.Images.Media.MIME_TYPE, Constants.MIME_TYPE)
            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
            put(MediaStore.Images.Media.RELATIVE_PATH, DIR_RELATIVE_PATH)
            put(MediaStore.Images.Media.IS_PENDING, true)
            put(MediaStore.Images.Media.DISPLAY_NAME, "${Constants.DISPLAY_NAME}$wallpaperId")
        }

        val uri: Uri? =
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        return uri?.let {
            bitmap.saveToStream(context, it)
            values.put(MediaStore.Images.Media.IS_PENDING, false)
            context.contentResolver.update(it, values, null, null)
            Timber.tag(tag).d("saveWallpaperToGallery - uri - $it")
            uri
        }
    }

    override suspend fun getBitmapFromRemote(imageUrl: String): Bitmap? {
        Timber.tag(tag).d("DownloadAndSaveWallpaperWork - bitmap from url - $imageUrl")
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false) // Disable hardware bitmaps.
            .build()

        val drawable = (loader.execute(request) as SuccessResult).drawable
        return (drawable as BitmapDrawable).bitmap
    }


    override fun getFileByWallpaperId(wallpaperId: String): File {
        val directory = ContextWrapper(context).getDir(Constants.DIR_IMAGES, Context.MODE_PRIVATE)
        val fileName = "${Constants.BACKUP_WALLPAPER}$wallpaperId${Constants.IMAGE_FILE_EXT}"
        return File(directory, fileName)
    }

    override fun deleteBackup(wallpaperId: String) {
        val file = getFileByWallpaperId(wallpaperId)
        file.delete()
        Timber.tag(tag).d("Deleted image $wallpaperId")
    }

    override fun deleteAllBackups() {
        val directory = ContextWrapper(context).getDir(Constants.DIR_IMAGES, Context.MODE_PRIVATE)
        directory.let {
            directory.deleteRecursively()
            Timber.tag(tag).d("Deleted all backups")
        }
    }

    override fun getBackup(wallpaperId: String): Bitmap? {
        val file = getFileByWallpaperId(wallpaperId)
        Timber.tag(tag).d("Restoring backup")
        return file.decodeBitmap()
    }
}