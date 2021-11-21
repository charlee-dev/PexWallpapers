package com.adrianwitaszak.notifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.camera2.params.RggbChannelVector.RED
import android.media.AudioAttributes
import android.media.RingtoneManager.TYPE_NOTIFICATION
import android.media.RingtoneManager.getDefaultUri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.adrianwitaszak.image_tools.ImageTools
import com.adwi.core.util.ext.runningOOrLater
import com.adwi.core.util.ext.runningSOrLater
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Defines notification channels
 */
enum class Channel {
    AUTO_WALLPAPER,
    RECOMMENDATIONS,
    INFO
}

/**
 * Notification tools
 *
 * @property context
 * @property imageTools
 * @property permissionTools
 * @constructor Create empty Notification tools
 */
@SuppressLint("NewApi")
class NotificationTools @Inject constructor(
    @ApplicationContext private val context: Context,
    private val imageTools: ImageTools,
) {
    private lateinit var channelId: String

    private val wallpaperGroupId = "wallpaper_group"
    private val appGroupId = "app_group"

    /**
     * Setup notifications
     *
     */
    fun setupNotifications() {
        if (runningOOrLater) {

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
            createNotificationChannel(Channel.AUTO_WALLPAPER)
            createNotificationChannel(Channel.RECOMMENDATIONS)
            createNotificationChannel(Channel.INFO)
        }
    }

    /**
     * Sets up the notification channels for API 26+.
     *
     * @param channel     channel id
     */
    private fun createNotificationChannel(channel: Channel) {
        if (runningOOrLater) {
            var name = ""
            var importance = 0
            val channelGroup: String

            when (channel) {
                Channel.AUTO_WALLPAPER -> {
                    channelId = "pex_auto_wallpaper"
                    name = "Auto wallpaper"
                    importance = NotificationManager.IMPORTANCE_DEFAULT
                    channelGroup = wallpaperGroupId
                }
                Channel.RECOMMENDATIONS -> {
                    channelId = "pex_recommendations"
                    name = "Recommendations"
                    importance = NotificationManager.IMPORTANCE_DEFAULT
                    channelGroup = wallpaperGroupId
                }
                Channel.INFO -> {
                    channelId = "info"
                    name = "Info"
                    importance = NotificationManager.IMPORTANCE_HIGH
                    channelGroup = appGroupId
                }
            }

            val ringtoneManager = getDefaultUri(TYPE_NOTIFICATION)
            val audioAttributes =
                AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()

            val notificationChannel = NotificationChannel(channelId, name, importance)
            notificationChannel.apply {
                group = channelGroup
                enableLights(true)
                lightColor = RED
                enableVibration(true)
                vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                setSound(ringtoneManager, audioAttributes)
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    /**
     * Send notification
     *
     * @param id
     * @param channelId
     * @param imageUrl
     * @param longMessage
     */
    @SuppressLint("UnspecifiedImmutableFlag")
    fun sendNotification(
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
                val onClickIntent = NavDeepLinkBuilder(context)
//                    .setGraph(R.navigation.nav_graph)
//                    .setDestination(R.id.favoritesFragment)
                    .createPendingIntent()

                val dismissPendingIntent = getPendingIntent(
                    Intent(context, OnDismissReceiver::class.java).apply {
                        putExtra(ACTION_AUTO, wallpaperId.toString())
                    }
                )

                val restorePendingIntent = getPendingIntent(
                    Intent(context, ActionRestoreReceiver::class.java).apply {
                        putExtra(ACTION_AUTO, wallpaperId.toString())
                    }
                )

                notification
                    .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                    .setContentTitle("New wallpaper set")
                    .setContentText("PexWallpapers just set an amazing wallpaper for you \nClick notification to edit favorites list")
                    .setLargeIcon(smallBitmap)
                    .setContentIntent(onClickIntent)
                    .setDeleteIntent(dismissPendingIntent)
                    .addAction(
                        R.drawable.ic_refresh,
                        context.getString(R.string.restore),
                        restorePendingIntent
                    )
                    .priority = NotificationCompat.PRIORITY_DEFAULT
            }
            Channel.RECOMMENDATIONS -> {
                notification
                    .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                    .setContentTitle("Recommendations")
                    .setContentText("PexWallpapers have some amazing wallpapers to offer for you")
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

    private fun getPendingIntent(intent: Intent): PendingIntent {
        val pendingIntentFlag = if (runningSOrLater)
            PendingIntent.FLAG_MUTABLE else PendingIntent.FLAG_ONE_SHOT
        return PendingIntent.getBroadcast(context, 1, intent, pendingIntentFlag)
    }
}

private const val WALLPAPER_REQUEST_CODE = 2019
private const val WALLPAPER_GROUP_ID = "wallpaper_group"
private const val APP_GROUP_ID = "app_group"
const val ACTION_AUTO = "action_auto"