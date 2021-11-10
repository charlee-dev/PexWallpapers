package com.adwi.datasource.network

import com.adwi.core.util.Constants.CURATED_PAGE_SIZE
import com.adwi.core.util.Constants.SEARCH_PAGE_SIZE
import com.adwi.datasource.network.domain.WallpaperResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PexService {

    @GET("v1/search")
    suspend fun searchWallpapers(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = SEARCH_PAGE_SIZE
    ): WallpaperResponse

    @GET("v1/curated")
    suspend fun getCuratedWallpapers(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = CURATED_PAGE_SIZE
    ): WallpaperResponse
}