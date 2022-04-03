package com.adwi.tool_automation.receivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.paging.ExperimentalPagingApi
import com.adrianwitaszak.tool_image.imagemanager.ImageManager
import com.adrianwitaszak.tool_image.wallpapersetter.WallpaperSetter
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
    lateinit var wallpaperSetter: WallpaperSetter

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context?, intent: Intent?) {
        val wallpaperId = intent?.getStringExtra(ACTION_AUTO)

        wallpaperId?.let {
            context?.run {
                val bitmap = imageManager.getBackup(wallpaperId)

                bitmap?.let {
                    wallpaperSetter.setWallpaper(
                        bitmap = bitmap,
                        home = true,
                        lock = false
                    )
                }

                imageManager.deleteBackup(wallpaperId)
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