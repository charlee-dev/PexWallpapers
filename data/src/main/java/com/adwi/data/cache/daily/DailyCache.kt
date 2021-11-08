package com.adwi.data.cache.daily

import com.adwi.data.cache.DailyEntity
import kotlinx.coroutines.flow.Flow

interface DailyCache {

    suspend fun insertDaily(wallpaper: DailyEntity)

    suspend fun getAllDaily(): Flow<List<DailyEntity>>

    suspend fun deleteAllDaily()
}