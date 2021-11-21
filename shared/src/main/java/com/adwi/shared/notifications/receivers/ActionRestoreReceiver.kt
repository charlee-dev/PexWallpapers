package com.adwi.shared.notifications.receivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.paging.ExperimentalPagingApi
import com.adrianwitaszak.work_notifications.R
import com.adwi.shared.util.Constants.ACTION_AUTO
import com.adwi.shared.work.WorkTools
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@AndroidEntryPoint
class ActionRestoreReceiver : BroadcastReceiver() {

    @Inject
    lateinit var workTools: WorkTools

    override fun onReceive(context: Context?, intent: Intent?) {
        val wallpaperId = intent?.getStringExtra(ACTION_AUTO)

        wallpaperId?.let {
            workTools.createRestoreWallpaperWork(wallpaperId)

            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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