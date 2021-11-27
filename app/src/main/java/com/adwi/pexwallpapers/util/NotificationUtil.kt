package com.adwi.pexwallpapers.util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.camera2.params.RggbChannelVector
import android.media.AudioAttributes
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.paging.ExperimentalPagingApi
import com.adwi.pexwallpapers.R
import com.adwi.pexwallpapers.util.receivers.ActionRestoreReceiver
import com.adwi.pexwallpapers.util.receivers.OnDismissReceiver
import kotlinx.coroutines.ExperimentalCoroutinesApi

enum class Channel {
    AUTO_WALLPAPER,
    RECOMMENDATIONS,
    INFO
}

object NotificationUtil {

    private lateinit var channelId: String

    private val wallpaperGroupId = "wallpaper_group"
    private val appGroupId = "app_group"

    fun setupNotifications(context: Context) {
//        if (runningOOrLater) {

        val wallpaperGroupName = context.getString(R.string.wallpapers)
        val appGroupName = context.getString(R.string.other)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannelGroup(
            NotificationChannelGroup(
                wallpaperGroupId,
                wallpaperGroupName
            )
        )
        notificationManager.createNotificationChannelGroup(
            NotificationChannelGroup(
                appGroupId,
                appGroupName
            )
        )
        createNotificationChannel(context, Channel.AUTO_WALLPAPER)
        createNotificationChannel(context, Channel.RECOMMENDATIONS)
        createNotificationChannel(context, Channel.INFO)
//        }
    }

    private fun createNotificationChannel(context: Context, channel: Channel) {
//        if (runningOOrLater) {
        var name = ""
        var importance = 0
        val channelGroup: String

        when (channel) {
            Channel.AUTO_WALLPAPER -> {
                val channelName = context.getString(R.string.auto_wallpapers)
                channelId = Constants.CHANNEL_AUTO
                name = channelName
                importance = NotificationManager.IMPORTANCE_DEFAULT
                channelGroup = wallpaperGroupId
            }
            Channel.RECOMMENDATIONS -> {
                val channelName = context.getString(R.string.recommendations)
                channelId = Constants.CHANNEL_RECOMMENDATIONS
                name = channelName
                importance = NotificationManager.IMPORTANCE_DEFAULT
                channelGroup = wallpaperGroupId
            }
            Channel.INFO -> {
                val channelName = context.getString(R.string.info)
                channelId = Constants.CHANNEL_INFO
                name = channelName
                importance = NotificationManager.IMPORTANCE_HIGH
                channelGroup = appGroupId
            }
        }

        val ringtoneManager = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val audioAttributes =
            AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()

        val notificationChannel = NotificationChannel(channelId, name, importance)
        notificationChannel.apply {
            group = channelGroup
            enableLights(true)
            lightColor = RggbChannelVector.RED
            enableVibration(true)
            vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            setSound(ringtoneManager, audioAttributes)
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
//        }
    }


    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    @SuppressLint("UnspecifiedImmutableFlag")
    fun sendNotification(
        context: Context,
        id: Int,
        channelId: Channel,
        bitmap: Bitmap,
        longMessage: String = "",
        wallpaperId: Int? = null,
        actionName: String? = null,
        actionId: String? = null,
        actionTitle: String = ""
    ) {
        val intentDestination: Class<*>

        val smallBitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_foreground)

        val notification = NotificationCompat.Builder(context, this.channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)

        when (channelId) {
            Channel.AUTO_WALLPAPER -> {
//                val onClickIntent = NavDeepLinkBuilder(context)
//                    .setGraph(R.navigation.nav_graph)
//                    .setDestination(R.id.favoritesFragment)
//                    .createPendingIntent()

                val dismissPendingIntent = getPendingIntent(context,
                    Intent(context, OnDismissReceiver::class.java).apply {
                        putExtra(Constants.ACTION_AUTO, wallpaperId.toString())
                    }
                )

                val restorePendingIntent = getPendingIntent(context,
                    Intent(context, ActionRestoreReceiver::class.java).apply {
                        putExtra(Constants.ACTION_AUTO, wallpaperId.toString())
                    }
                )

                val notificationTitle = context.getString(R.string.new_wallpaper_set)
                val notificationMessage =
                    context.getString(R.string.just_set_wallpaper_for_you, R.string.app_name)

                notification
                    .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationMessage)
                    .setLargeIcon(smallBitmap)
//                    .setContentIntent(onClickIntent)
                    .setDeleteIntent(dismissPendingIntent)
                    .addAction(
                        R.drawable.ic_refresh,
                        context.getString(R.string.restore),
                        restorePendingIntent
                    )
                    .priority = NotificationCompat.PRIORITY_DEFAULT
            }
            Channel.RECOMMENDATIONS -> {
                val title = context.getString(R.string.new_wallpaper_set)
                val message = context.getString(
                    R.string.have_some_amazing_wallpapers_for_you,
                    R.string.app_name
                )

                notification
                    .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                    .setContentTitle(title)
                    .setContentText(message)
                    .setLargeIcon(smallBitmap)
                    .priority = NotificationCompat.PRIORITY_DEFAULT
            }
            Channel.INFO -> {
                notification
                    .setStyle(NotificationCompat.BigTextStyle().bigText(longMessage))
                    .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_CHILDREN)
                    .setGroupSummary(true)
            }
        }

//        val notificationClickPendingIntent =
//            getPendingIntent(
//                Intent(context, intentDestination).apply {
//                    flags =
//                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    action = actionName
//                    putExtra(actionId, actionTitle)
//                })

//        notification.setContentIntent(notificationClickPendingIntent)

        with(NotificationManagerCompat.from(context)) {
            notify(id, notification.build())
        }
    }

    private fun getPendingIntent(context: Context, intent: Intent): PendingIntent {
        val runningSOrLater =
            android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S

        val pendingIntentFlag = if (runningSOrLater)
            PendingIntent.FLAG_MUTABLE else PendingIntent.FLAG_ONE_SHOT

        return PendingIntent.getBroadcast(context, 1, intent, pendingIntentFlag)
    }
}