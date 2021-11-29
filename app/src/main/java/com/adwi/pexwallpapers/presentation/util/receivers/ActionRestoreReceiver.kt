package com.adwi.pexwallpapers.presentation.util.receivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.paging.ExperimentalPagingApi
import com.adwi.pexwallpapers.R
import com.adwi.pexwallpapers.presentation.util.Constants.ACTION_AUTO
import com.adwi.pexwallpapers.presentation.work.workCreateRestoreWallpaperWork
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@AndroidEntryPoint
class ActionRestoreReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val wallpaperId = intent?.getStringExtra(ACTION_AUTO)

        wallpaperId?.let {
            context?.run {
                this.workCreateRestoreWallpaperWork(wallpaperId.toInt())

                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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