package com.adwi.tool_automation.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.camera2.params.RggbChannelVector
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.paging.ExperimentalPagingApi
import com.adwi.tool_automation.R
import com.adwi.tool_automation.receivers.ActionRestoreReceiver
import com.adwi.tool_automation.receivers.OnDismissReceiver
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
fun Context.sendAutoChangeWallpaperNotification(
    notificationManager: NotificationManager,
    bitmap: Bitmap,
    wallpaperId: Int
) {
    // Data for notification
    val title = getString(R.string.new_wallpaper_set)
    val message = getString(R.string.just_set_wallpaper_for_you)
    val smallBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_foreground)

    // Actions for notification
    val restorePendingIntent = getPendingIntent(
        Intent(this, ActionRestoreReceiver::class.java).apply {
            putExtra(Constants.ACTION_AUTO, wallpaperId.toString())
        }
    )

    val dismissPendingIntent = getPendingIntent(
        Intent(this, OnDismissReceiver::class.java).apply {
            putExtra(Constants.ACTION_AUTO, wallpaperId.toString())
        }
    )

    // Build notification
    val builder =
        NotificationCompat.Builder(this, getString(R.string.channel_id_auto_wallpapers))
            .apply { // Style
                setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                setContentTitle(title)
                setContentText(message)
                setSmallIcon(R.drawable.ic_launcher_foreground)
                setLargeIcon(smallBitmap)
            }
            .apply { // Type
                setCategory(NotificationCompat.CATEGORY_EVENT)
                setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                setDefaults(Notification.DEFAULT_SOUND)
                setDefaults(Notification.DEFAULT_VIBRATE)
                priority = NotificationCompat.PRIORITY_DEFAULT
            }
            .apply {  // Actions
                setAutoCancel(true)
                setDeleteIntent(dismissPendingIntent)
                addAction(
                    R.drawable.ic_refresh,
                    getString(R.string.restore),
                    restorePendingIntent
                )
            }

    // Send notification
    notificationManager.notify(wallpaperId, builder.build())
}

fun Context.createNotificationChannel(notificationManager: NotificationManager) {
    val channel = NotificationChannel(
        getString(R.string.channel_id_auto_wallpapers),
        getString(R.string.auto_wallpapers),
        NotificationManager.IMPORTANCE_DEFAULT,
    )

    channel.apply {
        lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
        enableVibration(true)
        channel.setBypassDnd(true)
        enableLights(true)
        lightColor = RggbChannelVector.GREEN_EVEN
        vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
    }
    notificationManager.createNotificationChannel(channel)
}

private fun Context.getPendingIntent(intent: Intent): PendingIntent {

    val pendingIntentFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.FLAG_MUTABLE
    } else {
        PendingIntent.FLAG_ONE_SHOT
    }

    return PendingIntent.getBroadcast(this, 1, intent, pendingIntentFlag)
}