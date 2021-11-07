package com.adwi.data.cache.wallpaper

import com.adwi.data.cache.CuratedEntity
import com.adwi.data.cache.WallpaperDb
import com.adwi.data.cache.WallpaperDbQueries
import com.adwi.data.cache.wallpaper.model.toDomain
import com.adwi.domain.Wallpaper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WallpaperCacheImpl(
    private val wallpaperDb: WallpaperDb
) : WallpaperCache {

    private var queries: WallpaperDbQueries = wallpaperDb.wallpaperDbQueries

    override fun getAllWallpapers(): Flow<List<Wallpaper>> = flow {
        queries.getAllWallpapers().executeAsList()
    }

    override suspend fun insertWallpaper(wallpaper: Wallpaper) {
        return wallpaper.run {
            queries.insertWallpaper(
                id,
                height,
                url,
                photographer,
                categoryName,
                isFavorite,
                updatedAt,
                imageUrlPortrait,
                imageUrlLandscape,
                imageUrlTiny
            )
        }
    }

    override suspend fun getWallpaperById(id: Long): Wallpaper {
        return queries.getWallpaperById(id).executeAsOne().toDomain()
    }

    override suspend fun getWallpapersOfCategory(name: String): List<Wallpaper> {
        return queries.getWallpapersOfCategory(name)
            .executeAsList()
            .map { it.toDomain() }
    }

    override suspend fun deleteWallpaper(id: Long) {
        return queries.deleteWallpaper(id)
    }

    override suspend fun updateWallpaperIsFavorite(wallpaper: Wallpaper) {
        return queries.updateWallpaperIsFavorite(wallpaper.isFavorite)
    }

    override suspend fun insertCurated(curatedEntity: CuratedEntity) {
        return curatedEntity.run {
            queries.insertCurated(this.wallpaperId)
        }
    }

    override suspend fun getAllCurated(): Flow<List<Wallpaper>> = flow {
        queries.getAllCurated().executeAsList()
    }

    override suspend fun deleteAllCuratedWallpapers() {
        return queries.deleteAllCuratedWallpapers()
    }


}