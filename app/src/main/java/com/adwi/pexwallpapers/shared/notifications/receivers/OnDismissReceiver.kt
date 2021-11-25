package com.adwi.pexwallpapers.shared.notifications.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.adwi.pexwallpapers.shared.image.ImageTools
import com.adwi.pexwallpapers.util.Constants.ACTION_AUTO
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class OnDismissReceiver : BroadcastReceiver() {

    @Inject
    lateinit var imageTools: ImageTools

    override fun onReceive(context: Context?, intent: Intent?) {
        val wallpaperId = intent?.getStringExtra(ACTION_AUTO)
        Toast.makeText(context, "OnDismissReceiver TEST $wallpaperId", Toast.LENGTH_SHORT).show()
        wallpaperId?.let {
            imageTools.deleteBackupBitmap(wallpaperId)
            Timber.tag("OnDismissReceiver").d("backup $wallpaperId deleted")
            Toast.makeText(context, "backup $wallpaperId deleted", Toast.LENGTH_SHORT).show()
        }
    }
}