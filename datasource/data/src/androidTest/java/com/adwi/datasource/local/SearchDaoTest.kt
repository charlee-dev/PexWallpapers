package com.adwi.datasource.local

import androidx.test.filters.SmallTest
import com.adwi.datasource.CoroutineAndroidTestRule
import com.adwi.datasource.local.dao.SearchDao
import com.adwi.datasource.local.dao.WallpapersDao
import com.adwi.datasource.local.entity.SearchMock
import com.adwi.datasource.local.entity.WallpaperMockAndroid
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import javax.inject.Inject
import javax.inject.Named


@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class SearchDaoTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val coroutineScope = CoroutineAndroidTestRule()

    @Inject
    @Named("test_database")
    lateinit var database: WallpaperDatabase

    private lateinit var searchDao: SearchDao
    private lateinit var wallpaperDao: WallpapersDao

    private val wallpaperList = WallpaperMockAndroid.list
    private val searchList = SearchMock.list

    @Before
    fun setup() {
        hiltRule.inject()
        searchDao = database.searchDao()
        wallpaperDao = database.wallpaperDao()
    }

    @After
    fun teardown() {
        database.close()
    }

//    @Test
//    fun getSearchResultWallpaperPaged_expectedTwoWallpapersCategoryFlowers_returnTrue() =
//        coroutineScope.dispatcher.runBlockingTest {
//
//            wallpaperDao.insertWallpapers(wallpaperList)
//            searchDao.insertSearchResults(searchList)
//
//            val actual = searchDao.getSearchResultWallpaperPaged("Flowers")
//            val flow = Pager(
//                config = PagingConfig(1, 1),
//                pagingSourceFactory = { actual }
//            ).flow
//
//            val wallpaper = flow.first()
//
//
//            val expected = listOf(WallpaperMockAndroid.first, WallpaperMockAndroid.forth)
//            // TODO() test paging
//            assertThat(wallpaper).isEqualTo(expected)
//        }

    @Test
    fun updateWallpaperFavorite_returnsTrue() = coroutineScope.dispatcher.runBlockingTest {

        wallpaperDao.insertWallpapers(wallpaperList)
        val wallpaper = WallpaperMockAndroid.first
        wallpaper.isFavorite = true
        wallpaperDao.updateWallpaper(wallpaper)

        val actual = wallpaperDao.getAllWallpapers().first()[0]
        Assert.assertEquals(wallpaper, actual)
    }


    @Test
    fun insertSearchResults_deleteSearchResultsForQuery_getLastQueryPosition_returnsTrue() =
        coroutineScope.dispatcher.runBlockingTest {

            wallpaperDao.insertWallpapers(wallpaperList)
            searchDao.insertSearchResults(searchList)

            val checking = searchDao.getLastQueryPosition("Flowers")
            // Checking if 2 wallpapers of query "Flowers" has been added
            assertThat(checking).isEqualTo(2)

            searchDao.deleteSearchResultsForQuery("Flowers")

            val actual = searchDao.getLastQueryPosition("Flowers")
            // Checking if 2 wallpapers of query "Flowers" has been removed
            assertThat(actual).isNull()
        }
}