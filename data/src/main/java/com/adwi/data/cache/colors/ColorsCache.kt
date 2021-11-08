package com.adwi.data.cache.colors

import com.adwi.data.cache.ColorsEntity
import kotlinx.coroutines.flow.Flow

interface ColorsCache {

    suspend fun insertColor(color: ColorsEntity)

    fun getAllColors(): Flow<List<ColorsEntity>>

    suspend fun getColor(colorName: String): ColorsEntity
}