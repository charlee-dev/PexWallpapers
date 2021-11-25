package com.adwi.repository.wallpaper

import androidx.paging.ExperimentalPagingApi
import com.adwi.pexwallpapers.CoroutineAndroidTestRule
import com.adwi.pexwallpapers.data.wallpapers.database.WallpaperDatabase
import com.adwi.pexwallpapers.data.wallpapers.database.domain.toEntity
import com.adwi.pexwallpapers.data.wallpapers.network.PexService
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class WallpaperRepositoryImplTest {

    @get:Rule(order = 1)
    val coroutineScope = com.adwi.pexwallpapers.CoroutineAndroidTestRule()

    private val database: com.adwi.pexwallpapers.data.wallpapers.database.WallpaperDatabase = mockk()
    private val service: com.adwi.pexwallpapers.data.wallpapers.network.PexService = mockk()

    private lateinit var wallpaperRepository: WallpaperRepository

    private val firstWallpaper = WallpapersMock.first
    private val secondWallpaper = WallpapersMock.second
    private val thirdWallpaper = WallpapersMock.third

    private val firstCurated = CuratedMock.first

    @Before
    fun setup() {
        wallpaperRepository = WallpaperRepositoryImpl(database, service)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun getCuratedTest_returnsCurated() {
        coroutineScope.dispatcher.runBlockingTest {

            database.wallpaperDao()
                .insertWallpapers(
                    listOf(
                        firstWallpaper.toEntity(),
                        secondWallpaper.toEntity(),
                        thirdWallpaper.toEntity()
                    )
                )

            database.wallpaperDao().insertCuratedWallpapers(listOf(firstCurated))

            val v = wallpaperRepository.getCurated(true, {}, {}).first()

            val data = v.data

            assertThat(data).hasSize(1)
        }
    }
}