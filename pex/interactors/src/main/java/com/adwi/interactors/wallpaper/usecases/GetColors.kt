package com.adwi.interactors.wallpaper.usecases

import androidx.room.withTransaction
import com.adwi.core.domain.DataState
import com.adwi.core.util.networkBoundResource
import com.adwi.datasource.local.WallpaperDatabase
import com.adwi.datasource.local.domain.ColorCategoryEntity
import com.adwi.datasource.network.PexService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GetColors @Inject constructor(
    private val service: PexService,
    private val database: WallpaperDatabase
) {
    private val categoryDao = database.categoryDao()

    fun execute(): Flow<DataState<List<ColorCategoryEntity>>> = networkBoundResource(
        query = {
            categoryDao.getAllColors()
        },
        fetch = {
            val colorList = mutableListOf<ColorCategoryEntity>()
            colorNameList.forEach { color ->
                val response = service.getColor(color)
                val wallpapers = response.wallpaperList
                colorList += ColorCategoryEntity(
                    name = color,
                    firstImage = wallpapers[0].src.tiny,
                    secondImage = wallpapers[1].src.tiny,
                    thirdImage = wallpapers[2].src.tiny,
                    forthImage = wallpapers[3].src.tiny,
                    timeStamp = System.currentTimeMillis()
                )
            }
            colorList.toList()
        },
        saveFetchResult = {
            database.withTransaction {
                categoryDao.insertColors(it)
            }
        },
        shouldFetch = { colors ->
            if (colors.isEmpty()) {
                true
            } else {
                val sortedWallpapers = colors.sortedBy { color ->
                    color.timeStamp
                }
                val oldestTimestamp = sortedWallpapers.firstOrNull()?.timeStamp
                val needsRefresh = oldestTimestamp == null ||
                        oldestTimestamp < System.currentTimeMillis() -
                        TimeUnit.DAYS.toMillis(7)
                needsRefresh
            }
        }
    )

    private val colorNameList = listOf(
        "Purple",
        "Orange",
        "White",
        "Black and White",
        "Violet",
        "Pink",
        "Yellow",
        "Blue",
        "Green",
        "White",
        "Black"
    )
}