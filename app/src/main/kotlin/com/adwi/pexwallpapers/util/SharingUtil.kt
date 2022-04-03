package com.adwi.pexwallpapers.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import com.adwi.pexwallpapers.R

fun Context.shareImage(
    uri: Uri,
    photographersName: String
) {
    val subject = getString(R.string.picture_by)
    val title = this.getString(com.adwi.tool_automation.R.string.just_set_wallpaper_for_you)
    val chooserMessage = this.getString(R.string.choose_an_app)

    val intent = Intent(Intent.ACTION_SEND).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        setDataAndType(uri, Constants.DATA_TYPE)
        putExtra(Intent.EXTRA_SUBJECT, subject + photographersName)
        putExtra(Intent.EXTRA_TITLE, title)
        putExtra(Intent.EXTRA_STREAM, uri)
    }

    ContextCompat.startActivity(
        this,
        Intent.createChooser(intent, chooserMessage),
        null
    )
}