package com.adrianwitaszak.sharing

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.adrianwitaszak.image_tools.ImageTools
import com.adwi.domain.Wallpaper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Sharing tools
 *
 * @property context
 * @property imageTools
 * @constructor Create empty Sharing tools
 */
class SharingTools @Inject constructor(
    @ApplicationContext private val context: Context,
    private val imageTools: ImageTools
) {

    /**
     * Open url in browser
     *
     * @param url
     */
    fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.parse(url)
        }
        startActivity(context, intent, null)
    }

    /**
     * Contact support
     *
     */
    fun contactSupport() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.parse("mailto:adrianwitaszak@gmial.com")
            putExtra(Intent.EXTRA_SUBJECT, "Support request Nr. 12345678")
        }
        startActivity(context, Intent.createChooser(intent, "Choose an app"), null)
    }

    /**
     * Share image
     *
     * @param context Application Context
     * @param imageUrl
     * @param photographer
     */
    fun shareImage(activity: AppCompatActivity, uri: Uri, wallpaper: Wallpaper) {

        val intent = Intent(Intent.ACTION_SEND).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            setDataAndType(uri, "image/*")
            putExtra(Intent.EXTRA_SUBJECT, "Picture by ${wallpaper.photographer}")
            putExtra(Intent.EXTRA_TITLE, "Picture by PexWallpapers")
            putExtra(Intent.EXTRA_STREAM, uri)
        }

        startActivity(
            activity,
            Intent.createChooser(intent, "Choose an app"),
            null
        )
    }
}