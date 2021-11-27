package com.adwi.pexwallpapers.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import com.adwi.pexwallpapers.R


fun Context.contactSupport() {
    val email = Constants.SUPPORT_EMAIL
    val subject =
        "${this.getString(R.string.support_title)} 12345678" // TODO(implement support messaging)
    val chooserMessage = this.getString(R.string.support_chooser_message)

    val intent = Intent(Intent.ACTION_SENDTO).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        data = Uri.parse(email)
        putExtra(Intent.EXTRA_SUBJECT, subject)
    }

    ContextCompat.startActivity(this, Intent.createChooser(intent, chooserMessage), null)
}

fun Context.shareImage(
    uri: Uri,
    photographersName: String
) {
    val subject = this.getString(R.string.picture_by, photographersName)
    val title = this.getString(R.string.picture_by, R.string.app_name)
    val chooserMessage = this.getString(R.string.choose_an_app)

    val intent = Intent(Intent.ACTION_SEND).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        setDataAndType(uri, Constants.DATA_TYPE)
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TITLE, title)
        putExtra(Intent.EXTRA_STREAM, uri)
    }

    ContextCompat.startActivity(
        this,
        Intent.createChooser(intent, chooserMessage),
        null
    )
}