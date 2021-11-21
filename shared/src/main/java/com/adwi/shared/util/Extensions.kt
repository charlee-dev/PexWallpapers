package com.adwi.shared.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

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