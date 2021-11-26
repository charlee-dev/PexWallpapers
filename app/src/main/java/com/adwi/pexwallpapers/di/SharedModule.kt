package com.adwi.pexwallpapers.di

import android.app.WallpaperManager
import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.work.WorkManager
import com.adwi.pexwallpapers.data.settings.SettingsDao
import com.adwi.pexwallpapers.shared.image.ImageTools
import com.adwi.pexwallpapers.shared.notifications.NotificationTools
import com.adwi.pexwallpapers.shared.setter.WallpaperSetter
import com.adwi.pexwallpapers.shared.sharing.SharingTools
import com.adwi.pexwallpapers.shared.work.WorkTools
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedModule {

//    @Singleton
//    @Provides
//    fun provideLogger() = Logger()

    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    @Singleton
    @Provides
    fun provideWorkTools(
        workManager: WorkManager,
        settingsDao: SettingsDao
    ) = WorkTools(workManager, settingsDao)

    @Singleton
    @Provides
    fun provideWorkManager(
        @ApplicationContext context: Context
    ) = WorkManager.getInstance(context)

    @Provides
    @Singleton
    fun provideWallpaperManager(
        @ApplicationContext context: Context
    ): WallpaperManager = WallpaperManager.getInstance(context)

    @Provides
    @Singleton
    fun provideWallpaperTools(
        @ApplicationContext context: Context,
        wallpaperManager: WallpaperManager
    ): WallpaperSetter = WallpaperSetter(context, wallpaperManager)

    @Provides
    @Singleton
    fun provideSharingTools(
        @ApplicationContext context: Context
    ): SharingTools = SharingTools(context)

    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideNotificationTools(
        @ApplicationContext context: Context
    ): NotificationTools = NotificationTools(context)

    @Provides
    @Singleton
    fun provideImageTools(
        @ApplicationContext context: Context
    ): ImageTools = ImageTools(context)
}