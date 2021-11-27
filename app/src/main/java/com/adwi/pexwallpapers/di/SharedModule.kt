package com.adwi.pexwallpapers.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SharedModule {

//    @Singleton
//    @Provides
//    fun provideLogger() = Logger()
}