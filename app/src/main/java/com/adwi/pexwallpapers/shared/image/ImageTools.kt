package com.adwi.pexwallpapers.shared.image

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
import com.adwi.pexwallpapers.util.Constants.BACKUP_WALLPAPER
import com.adwi.pexwallpapers.util.Constants.DIR_IMAGES
import com.adwi.pexwallpapers.util.Constants.DIR_RELATIVE_PATH
import com.adwi.pexwallpapers.util.Constants.DISPLAY_NAME
import com.adwi.pexwallpapers.util.Constants.IMAGE_FILE_EXT
import com.adwi.pexwallpapers.util.Constants.MIME_TYPE
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.File
import java.io.OutputStream
import javax.inject.Inject


class ImageTools @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun fetchRemoteAndSaveLocally(id: Int, url: String): Uri? {
        val bitmap = getBitmapFromRemote(url)
        val uri = bitmap?.let { backupImageToLocal(id, bitmap) }
        Timber.tag(TAG).d("fetchRemoteAndSaveLocally - $uri")
        return uri
    }

    suspend fun fetchRemoteAndSaveToGallery(id: Int, url: String): Uri? {
        val bitmap = getBitmapFromRemote(url)
        val uri = bitmap?.let { saveImageToGallery(id, bitmap) }
        Timber.tag(TAG).d("fetchRemoteAndSaveToGallery - $uri")
        return uri
    }

    suspend fun getBitmapFromRemote(imageUrl: String): Bitmap? {
        val loader = ImageLoader(context)

        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false) // Disable hardware bitmaps.
            .build()

        val drawable = (loader.execute(request) as SuccessResult).drawable
        return (drawable as BitmapDrawable).bitmap
    }

    fun getBitmapFromLocal(wallpaperId: Int): Bitmap? {
        return try {
            val file = getFileByWallpaperId(wallpaperId.toString())
            file.decodeBitmap()
        } catch (e: Exception) {
            Timber.tag(TAG).d(e.localizedMessage ?: "bitmapFromLocal - Failed")
            null
        }

    }

    fun deleteBackupBitmap(wallpaperId: String) {
        val file = getFileByWallpaperId(wallpaperId)
        file.delete()
        Timber.tag(TAG).d("deleteBackupBitmap - Deleted image $wallpaperId")
    }

    fun deleteAllBackups() {
        val directory = ContextWrapper(context).getDir(DIR_IMAGES, Context.MODE_PRIVATE)

        directory.let {
            directory.deleteRecursively()
            Timber.tag(TAG).d("Deleted directory - images")
        }

        // TODO(decide if code is safe to delete)
//        if (directory.exists()) {
//            directory.deleteRecursively()
//            Timber.tag(TAG).d("Deleted directory - images")
//        } else {
//            Timber.tag(TAG).d("Directory - images - doesn't exist")
//        }
    }

    fun backupImageToLocal(wallpaperId: Int, bitmap: Bitmap): Uri? {
        val file = getFileByWallpaperId(wallpaperId.toString())
        file.compressStream(bitmap)
        return file.getUri()
    }

    fun restoreBackup(wallpaperId: String): Bitmap? {
        val file = getFileByWallpaperId(wallpaperId)
        return file.decodeBitmap()
    }


    private fun getFileByWallpaperId(wallpaperId: String): File {
        val directory = ContextWrapper(context).getDir(DIR_IMAGES, Context.MODE_PRIVATE)
        val fileName = "$BACKUP_WALLPAPER$wallpaperId$IMAGE_FILE_EXT"
        return File(directory, fileName)
    }

    // Images saved in sdcard/Pictures/pex_wallpapers/
    @SuppressLint("InlinedApi")
    fun saveImageToGallery(id: Int, bitmap: Bitmap): Uri? {
//        if (runningQOrLater) {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, MIME_TYPE)
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        values.put(MediaStore.Images.Media.RELATIVE_PATH, DIR_RELATIVE_PATH)
        values.put(MediaStore.Images.Media.IS_PENDING, true)
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "$DISPLAY_NAME$id")

        val uri: Uri? =
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        if (uri != null) {
            saveImageToStream(bitmap, context.contentResolver.openOutputStream(uri))

            values.put(MediaStore.Images.Media.IS_PENDING, false)
//                values.put(MediaStore.Images.Media.MIME_TYPE, "image/*")

            context.contentResolver.update(uri, values, null, null)
            return uri
        }
//        } else {
//            val directory = File(
//                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//                    .toString() + File.separator + "pex_wallpapers"
//            )
//
//            if (!directory.exists()) {
//                directory.mkdirs()
//            }
//
//            val fileName = "img_$id.jpeg"
//            val file = File(directory, fileName)
//            saveImageToStream(bitmap, FileOutputStream(file))
//
//            val values = ContentValues()
//            values.put(
//                MediaStore.Images.Media.DATA,
//                file.absolutePath
//            ) // .DATA is deprecated in API 29
//
//            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//            return Uri.fromFile(file)
//        }
        return null
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

private const val TAG = "ImageTools"