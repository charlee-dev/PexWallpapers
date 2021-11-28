package com.adwi.pexwallpapers.presentation.util.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.adwi.pexwallpapers.presentation.util.Constants.ACTION_AUTO
import com.adwi.pexwallpapers.presentation.util.deleteBackupBitmap
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class OnDismissReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val wallpaperId = intent?.getStringExtra(ACTION_AUTO)
        Toast.makeText(context, "OnDismissReceiver TEST $wallpaperId", Toast.LENGTH_SHORT).show()
        wallpaperId?.let {
            context?.deleteBackupBitmap(wallpaperId)
            Timber.tag("OnDismissReceiver").d("backup $wallpaperId deleted")
            Toast.makeText(context, "backup $wallpaperId deleted", Toast.LENGTH_SHORT).show()
        }
    }
}