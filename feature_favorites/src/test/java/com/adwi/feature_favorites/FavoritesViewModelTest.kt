package com.adwi.feature_favorites

import androidx.paging.ExperimentalPagingApi
import app.cash.turbine.test
import com.adwi.data.database.dao.WallpapersDao
import com.adwi.data.database.domain.toEntityList
import com.adwi.feature_favorites.presentation.FavoritesViewModel
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.adwi.test_utils.CoroutineTestRule
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
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
    private lateinit var wallpapersDao: WallpapersDao

    private val wallpaper1 = Wallpaper()
    private val wallpaper2 = Wallpaper()
    private val wallpaperList = listOf(wallpaper1, wallpaper2)

    @get:Rule
    val rule = CoroutineTestRule()

    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    @Before
    fun setup() {
        wallpapersDao = mockk(relaxed = true)
        viewModel = FavoritesViewModel(wallpapersDao, rule.testDispatcher)

        rule.testDispatcher.runBlockingTest {
            wallpapersDao.insertWallpapers(wallpaperList.toEntityList())
        }
    }


    @After
    fun tearDown() {
        rule.testDispatcher.runBlockingTest {
            wallpapersDao.resetAllFavorites()
        }
    }

    @Test
    fun `tests - getFavorites and onFavoriteClick, returns list with one favorite`() =
        rule.testDispatcher.runBlockingTest {
            viewModel.onFavoriteClick(wallpaper1)

            viewModel.wallpapers.test {
                assertThat(awaitItem().size).isEqualTo(0)
                cancelAndConsumeRemainingEvents()
            }
        }
}