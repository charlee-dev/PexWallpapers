package com.adwi.data.network

import com.adwi.data.Constants.CURATED_PAGE_SIZE
import com.adwi.data.Constants.DAILY_PAGE_SIZE
import com.adwi.data.Constants.DEFAULT_DAILY_CATEGORY
import com.adwi.data.Constants.PAGING_PAGE_SIZE
import com.adwi.data.network.domain.WallpaperResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PexService {

    @GET("v1/search")
    suspend fun getDailyWallpapers(
        @Query("query") categoryName: String = DEFAULT_DAILY_CATEGORY,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = DAILY_PAGE_SIZE
    ): WallpaperResponse

    @GET("v1/curated")
    suspend fun getCuratedWallpapers(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = CURATED_PAGE_SIZE
    ): WallpaperResponse

    @GET("v1/search")
    suspend fun getSearch(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = PAGING_PAGE_SIZE
    ): WallpaperResponse
}