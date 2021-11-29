package com.adwi.pexwallpapers.presentation.app

import android.app.NotificationManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}