package com.adwi.pexwallpapers

import android.content.Context
import androidx.room.Room
import com.adwi.pexwallpapers.data.database.wallpapers.WallpaperDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import javax.inject.Named

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Provides
    @Named("test_database")
    fun provideInMemoryDatabase(
        @ApplicationContext context: Context
    ) = Room.inMemoryDatabaseBuilder(
        context,
        WallpaperDatabase::class.java
    ).allowMainThreadQueries()
        .setTransactionExecutor(testDispatcher.asExecutor())
        .setQueryExecutor(testDispatcher.asExecutor())
        .build()
}