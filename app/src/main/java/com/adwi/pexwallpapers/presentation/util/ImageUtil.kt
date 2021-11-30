package com.adwi.pexwallpapers.presentation.util

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


suspend fun Context.fetchRemoteAndSaveLocally(id: Int, url: String): Uri? {

    // Fetch remote
    val bitmapResult = handleGetBitmapFromRemoteResult(url)

    // Save locally
    val uri = bitmapResult?.let { backupImageToLocal(id, it) }

    Timber.tag(TAG).d("fetchRemoteAndSaveLocally - $uri")
    return uri
}

suspend fun Context.fetchRemoteAndSaveToGallery(id: Int, url: String): Uri? {

    // Fetch remote
    val bitmapResult = handleGetBitmapFromRemoteResult(url)

    // Save to gallery
    val uri = bitmapResult?.let { saveImageToGallery(id, it) }

    Timber.tag(TAG).d("fetchRemoteAndSaveToGallery - $uri")
    return uri
}

suspend fun Context.handleGetBitmapFromRemoteResult(imageUrl: String) =
    when (val result = getBitmapFromRemote(imageUrl)) {
        is com.adwi.core.DataState.Error -> {
            Timber.tag(TAG).d(result.error?.localizedMessage ?: "Cant fetch from remote")
            null
        }
        is com.adwi.core.DataState.Loading -> {
            null
        }
        is com.adwi.core.DataState.Success -> {
            result.data
        }
    }

private suspend fun Context.getBitmapFromRemote(imageUrl: String): com.adwi.core.DataState<Bitmap?> {
    return try {
        val loader = ImageLoader(this)

        val request = ImageRequest.Builder(this)
            .data(imageUrl)
            .allowHardware(false) // Disable hardware bitmaps.
            .build()

        val drawable = (loader.execute(request) as SuccessResult).drawable
        val bitmap = (drawable as BitmapDrawable).bitmap
        com.adwi.core.DataState.Success(bitmap)
    } catch (e: Throwable) {
        Timber.tag(TAG).d(e.localizedMessage)
        com.adwi.core.DataState.Error(e)
    }
}

fun Context.getBitmapFromLocal(wallpaperId: Int): Bitmap? {
    return try {
        val file = getFileByWallpaperId(wallpaperId.toString())
        file.decodeBitmap()
    } catch (e: Exception) {
        Timber.tag(TAG).d(e.localizedMessage ?: "bitmapFromLocal - Failed")
        null
    }

}

fun Context.deleteBackupBitmap(wallpaperId: String) {
    val file = getFileByWallpaperId(wallpaperId)
    file.delete()
    Timber.tag(TAG).d("deleteBackupBitmap - Deleted image $wallpaperId")
}

fun Context.deleteAllBackups() {
    val directory = ContextWrapper(this).getDir(Constants.DIR_IMAGES, Context.MODE_PRIVATE)

    directory.let {
        directory.deleteRecursively()
        Timber.tag(TAG).d("Deleted directory - images")
    }
}

fun Context.backupImageToLocal(wallpaperId: Int, bitmap: Bitmap): Uri? {
    val file = getFileByWallpaperId(wallpaperId.toString())
    file.compressStream(bitmap)
    return file.getUri()
}

fun Context.restoreBackup(wallpaperId: String): Bitmap? {
    val file = getFileByWallpaperId(wallpaperId)
    return file.decodeBitmap()
}


private fun Context.getFileByWallpaperId(wallpaperId: String): File {
    val directory = ContextWrapper(this).getDir(Constants.DIR_IMAGES, Context.MODE_PRIVATE)
    val fileName = "${Constants.BACKUP_WALLPAPER}$wallpaperId${Constants.IMAGE_FILE_EXT}"
    return File(directory, fileName)
}

// Images saved in sdcard/Pictures/pex_wallpapers/
@SuppressLint("InlinedApi")
fun Context.saveImageToGallery(id: Int, bitmap: Bitmap): Uri? {
    val values = ContentValues()
    values.put(MediaStore.Images.Media.MIME_TYPE, Constants.MIME_TYPE)
    values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
    values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
    values.put(MediaStore.Images.Media.RELATIVE_PATH, Constants.DIR_RELATIVE_PATH)
    values.put(MediaStore.Images.Media.IS_PENDING, true)
    values.put(MediaStore.Images.Media.DISPLAY_NAME, "${Constants.DISPLAY_NAME}$id")

    val uri: Uri? = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

    if (uri != null) {
        bitmap.saveImageToStream(contentResolver.openOutputStream(uri))

        values.put(MediaStore.Images.Media.IS_PENDING, false)

        contentResolver.update(uri, values, null, null)
        return uri
    }
    return null
}

private fun Bitmap.saveImageToStream(outputStream: OutputStream?) {
    if (outputStream != null) {
        try {
            compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

internal fun File?.decodeBitmap(): Bitmap? = this?.let {
    BitmapFactory.decodeFile(this.absolutePath)
}

internal fun File?.getUri(): Uri? = this?.let {
    Uri.parse(this.absolutePath)
}

internal fun File?.toStream(): OutputStream = FileOutputStream(this)

internal fun File?.compressStream(bitmap: Bitmap) {
    val stream: OutputStream = this.toStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    stream.flush()
    stream.close()
}

private const val TAG = "ImageTools"