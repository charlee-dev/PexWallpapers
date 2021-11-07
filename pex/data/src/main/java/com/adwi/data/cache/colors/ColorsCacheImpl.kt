package com.adwi.data.cache.colors

import com.adwi.data.cache.ColorsEntity
import com.adwi.data.cache.WallpaperDb
import com.adwi.data.cache.WallpaperDbQueries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ColorsCacheImpl(
    private val wallpaperDb: WallpaperDb
) : ColorsCache {

    private var queries: WallpaperDbQueries = wallpaperDb.wallpaperDbQueries

    override suspend fun insertColor(color: ColorsEntity) {
        return color.run {
            queries.insertColor(name, firstImage, secondImage, thirdImage, forthImage, timeStamp)
        }
    }

    override fun getAllColors(): Flow<List<ColorsEntity>> = flow {
        queries.getAllColors().executeAsList()
    }


    override suspend fun getColor(colorName: String): ColorsEntity =
        queries.getColor(colorName).executeAsOne()
}