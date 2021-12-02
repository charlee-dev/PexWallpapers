package com.adwi.data.fake

import com.adwi.data.database.dao.WallpapersDao
import com.adwi.data.database.domain.CuratedEntity
import com.adwi.data.database.domain.WallpaperEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeWallpapersDao : WallpapersDao {

    private var wallpaperList = mutableListOf<WallpaperEntity>()
    private var curatedList = mutableListOf<CuratedEntity>()

    private val wallpapersFlow = MutableStateFlow<List<WallpaperEntity>>(wallpaperList)
    private val curatedFlow = MutableStateFlow<List<CuratedEntity>>(curatedList)

    override suspend fun insertWallpapers(wallpapers: List<WallpaperEntity>) {
        wallpapers.forEach { wallpaper ->
            wallpaperList.add(wallpaper)
        }
        syncLists()
    }

    override fun getAllWallpapers(): Flow<List<WallpaperEntity>> =
        flowOf(wallpapersFlow.value)

    override fun getWallpaperById(wallpaperId: Int): Flow<WallpaperEntity> = flow {
        val wallpaper = wallpapersFlow.value.first { wallpaper ->
            wallpaper.id == wallpaperId
        }
        emit(wallpaper)
    }

    override fun getWallpapersOfCategory(categoryName: String): Flow<List<WallpaperEntity>> = flow {
        val wallpaper = wallpapersFlow.value.filter { wallpaper ->
            wallpaper.categoryName == categoryName
        }
        emit(wallpaper)
    }

    override suspend fun updateWallpaper(wallpaper: WallpaperEntity) {
        val item = wallpaperList.find {
            wallpaper == it
        }
        item?.let {
            wallpaperList.remove(item)
        }
        wallpaperList.add(wallpaper)
    }

    override suspend fun updateWallpaperIsFavorite(id: Int, isFavorite: Boolean) {
        val itemId = wallpaperList.indexOfFirst { wallpaper ->
            wallpaper.id == id
        }
        wallpaperList[itemId].isFavorite = isFavorite
        syncLists()
    }

    override fun getAllFavorites(): Flow<List<WallpaperEntity>> = flowOf(
        wallpaperList.filter { it.isFavorite }
    )

    override suspend fun resetAllFavorites() {
        wallpaperList.forEach { it.isFavorite = false }
        syncLists()
    }

    override suspend fun deleteNonFavoriteWallpapersOlderThan(timestampInMillis: Long) {
        wallpaperList.forEach {
            if (it.updatedAt < timestampInMillis) {
                wallpaperList.remove(it)
            }
        }
        syncLists()
    }

    override suspend fun insertCuratedWallpapers(wallpapers: List<CuratedEntity>) {
        wallpapers.forEach { curated ->
            curatedList.add(curated)
        }
        curatedFlow.value = curatedList
        syncLists()
    }

    override fun getAllCuratedWallpapers(): Flow<List<WallpaperEntity>> = flowOf(wallpaperList)

    override suspend fun deleteAllCuratedWallpapers() {
        curatedList.forEach {
            curatedList.remove(it)
        }
        syncLists()
    }

    fun syncLists() {
        wallpapersFlow.value = wallpaperList
        curatedFlow.value = curatedList
    }

    fun emptyLists() {
        wallpaperList = mutableListOf()
        curatedList = mutableListOf()
        syncLists()
    }
}