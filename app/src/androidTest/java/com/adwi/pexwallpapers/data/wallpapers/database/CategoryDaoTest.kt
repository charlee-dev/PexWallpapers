package com.adwi.pexwallpapers.data.wallpapers.database

import androidx.test.filters.SmallTest
import com.adwi.pexwallpapers.CoroutineAndroidTestRule
import com.adwi.pexwallpapers.data.database.wallpapers.WallpaperDatabase
import com.adwi.pexwallpapers.data.database.wallpapers.dao.CategoryDao
import com.adwi.pexwallpapers.data.wallpapers.database.entity.CategoryMock
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@ExperimentalCoroutinesApi
@SmallTest
class CategoryDaoTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val coroutineScope = CoroutineAndroidTestRule()

    @Inject
    @Named("test_database")
    lateinit var database: WallpaperDatabase

    private lateinit var categoryDao: CategoryDao

    private val firstCategory = CategoryMock.first
    private val secondCategory = CategoryMock.second

    @Before
    fun setup() {
        hiltRule.inject()
        categoryDao = database.categoryDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertColorCategory_getBackListWithOneColor_returnTrue() =
        coroutineScope.dispatcher.runBlockingTest {

            val expected = listOf(firstCategory)

            categoryDao.insertColors(expected)

            val actual = categoryDao.getAllColors().first()
            assertThat(actual).isEqualTo(expected)
        }

    @Test
    fun insertTwoCategories_getOneSpecificCategoryBack_returnsTrue() =
        coroutineScope.dispatcher.runBlockingTest {
            val list = listOf(firstCategory, secondCategory)

            categoryDao.insertColors(list)

            val actual = categoryDao.getColor("Purple")

            Assert.assertEquals(secondCategory, actual)
        }

    @Test
    fun update_insertFirst_changeName_checkNameChanged_returnsTrue() =
        coroutineScope.dispatcher.runBlockingTest {
            val list = listOf(firstCategory)

            categoryDao.insertColors(list)

            val checkList = categoryDao.getAllColors().first()

            assertThat(checkList).hasSize(1)

            val category = checkList.first()
            val updatedCategory = category.copy(firstImage = "firstImage")

            categoryDao.updateColor(updatedCategory)

            val actual = categoryDao.getColor("Orange")

            println(actual.name)
            Assert.assertEquals(updatedCategory.firstImage, actual.firstImage)
        }
}