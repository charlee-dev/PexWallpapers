package com.adwi.pexwallpapers.presentation.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.paging.ExperimentalPagingApi
import androidx.work.Configuration
import com.adwi.pexwallpapers.BuildConfig
import com.adwi.pexwallpapers.presentation.util.NotificationUtil
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import javax.inject.Inject


@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltAndroidApp
class PexApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory


    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        NotificationUtil.setupNotifications(this)
    }
}