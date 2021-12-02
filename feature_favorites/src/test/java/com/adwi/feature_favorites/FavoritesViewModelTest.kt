package com.adwi.feature_favorites

import androidx.paging.ExperimentalPagingApi
import app.cash.turbine.test
import com.adwi.data.database.domain.toEntityList
import com.adwi.data.fake.FakeWallpapersDao
import com.adwi.feature_favorites.presentation.FavoritesViewModel
import com.adwi.tests.CoroutineTestRule
import com.adwi.tests.WallpapersMock
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.time.ExperimentalTime

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@ExperimentalTime
@RunWith(JUnit4::class)
class FavoritesViewModelTest {

    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var wallpapersDao: FakeWallpapersDao

    private val wallpaper1 = WallpapersMock.first
    private val wallpaper2 = WallpapersMock.second
    private val wallpaper3 = WallpapersMock.third
    private val wallpaperList = listOf(wallpaper1, wallpaper2, wallpaper3)

    @get:Rule
    val rule = CoroutineTestRule()

    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    @Before
    fun setup() {
        wallpapersDao = FakeWallpapersDao()
        viewModel = FavoritesViewModel(wallpapersDao, rule.testDispatcher)

        rule.testDispatcher.runBlockingTest {
            wallpapersDao.insertWallpapers(wallpaperList.toEntityList())
        }
    }


    @After
    fun tearDown() {
        rule.testDispatcher.runBlockingTest {
            wallpapersDao.emptyLists()
        }
    }

    @Test
    fun `tests - getFavorites and onFavoriteClick, returns list with one favorite`() =
        rule.testDispatcher.runBlockingTest {
            viewModel.onFavoriteClick(wallpaper1)

            viewModel.getFavorites().test {
                assertThat(awaitItem().size).isEqualTo(1)
                cancelAndConsumeRemainingEvents()
            }
        }
}