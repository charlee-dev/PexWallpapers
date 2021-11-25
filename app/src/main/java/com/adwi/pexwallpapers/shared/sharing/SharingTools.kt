package com.adwi.pexwallpapers.shared.sharing

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.adwi.pexwallpapers.R
import com.adwi.pexwallpapers.util.Constants.DATA_TYPE
import com.adwi.pexwallpapers.util.Constants.SUPPORT_EMAIL
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class SharingTools @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.parse(url)
        }

        startActivity(context, intent, null)
    }


    fun contactSupport() {
        val email = SUPPORT_EMAIL
        val subject =
            "${context.getString(R.string.support_title)} 12345678" // TODO(implement support messaging)
        val chooserMessage = context.getString(R.string.support_chooser_message)

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            data = Uri.parse(email)
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }

        startActivity(context, Intent.createChooser(intent, chooserMessage), null)
    }


    fun shareImage(
        activity: Activity,
        uri: Uri,
        photographersName: String
    ) {
        val subject = context.getString(R.string.picture_by, photographersName)
        val title = context.getString(R.string.picture_by, R.string.app_name)
        val chooserMessage = context.getString(R.string.choose_an_app)

        val intent = Intent(Intent.ACTION_SEND).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            setDataAndType(uri, DATA_TYPE)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TITLE, title)
            putExtra(Intent.EXTRA_STREAM, uri)
        }

        startActivity(
            activity,
            Intent.createChooser(intent, chooserMessage),
            null
        )
    }
}