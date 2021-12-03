package com.adwi.tool_automation.receivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.paging.ExperimentalPagingApi
import com.adrianwitaszak.tool_image.ImageManager
import com.adwi.core.Resource
import com.adwi.tool_automation.R
import com.adwi.tool_automation.util.Constants.ACTION_AUTO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@AndroidEntryPoint
class ActionRestoreReceiver : BroadcastReceiver() {

    @Inject
    lateinit var imageManager: ImageManager
    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context?, intent: Intent?) {
        val wallpaperId = intent?.getStringExtra(ACTION_AUTO)

        wallpaperId?.let {
            context?.run {
                // Get wallpaper backed up earlier
                val bitmap = imageManager.restoreBackup(wallpaperId)
                // try to set wallpaper
                val result = bitmap?.let {
                    imageManager.setWallpaper(
                        bitmap = bitmap,
                        home = true,
                        lock = false
                    )
                }
                // if success delete backup and notify user
                if (result == Resource.Success()) {
                    imageManager.deleteBackupBitmap(wallpaperId)
                    notificationManager.cancel(wallpaperId.toInt())

                    Timber.tag("OnDismissReceiver")
                        .d(context.getString(R.string.previous_wallpaper_has_been_restored))
                    Toast.makeText(
                        context,
                        context.getString(R.string.previous_wallpaper_has_been_restored),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}