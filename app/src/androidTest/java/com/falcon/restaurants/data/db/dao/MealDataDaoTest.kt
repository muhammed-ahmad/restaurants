package com.falcon.meals.data.db.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.falcon.restaurants.data.InstrumentedTestData
import com.falcon.restaurants.data.db.RoomDB
import com.falcon.restaurants.data.db.dao.MealDataDao
import com.falcon.restaurants.data.db.model.MealData
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

@RunWith(AndroidJUnit4::class)
class MealDataDaoTest {

    companion object {
        const val TAG: String = "MealDataDaoTest"
        val mealsTestData: List<MealData> = InstrumentedTestData.createMealDatas()
        lateinit var SUT: MealDataDao
        lateinit var db: RoomDB

        @BeforeClass
        @JvmStatic
        fun setUp() {
            val context: Context = ApplicationProvider.getApplicationContext()
            db = Room.inMemoryDatabaseBuilder( context, RoomDB::class.java).allowMainThreadQueries().build()
        }

        @AfterClass
        @JvmStatic
        fun closeDb(){
            db.close()
        }
    }

    @Rule
    @JvmField
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUpForEachMethod() {
        SUT = Mockito.spy(db.mealDao())
        db.clearAllTables()
        SUT.upsert(mealsTestData)
        Mockito.reset(SUT)
    }

    @Test
    fun getMealsByRestaurantId_WhenRestaurantIdFound_ReturnThisRestaurantMeals() {
        // arrange
        val restaurantId = "id2"
        // act
        val testObserver: TestObserver<List<MealData>> = SUT.getMealsByRestaurantId(restaurantId).test()
        // assert
        testObserver.assertValue { meals -> meals.size == 2 }
        testObserver.assertValue { meals -> meals[0].restaurantId.equals("id2") }
        testObserver.assertValueCount(1)
    }

    @Test
    fun getMealsByRestaurantId_WhenRestaurantIdNotFound_ReturnEmptyList() {
        // arrange
        val notFoundId = "id12"
        // act
        val testObserver: TestObserver<List<MealData>> = SUT.getMealsByRestaurantId(notFoundId).test()
        testObserver.assertValue { meals -> meals.size == 0 }
        testObserver.assertValueCount(1)
    }

    @Test
    fun getMaxUpdated_WhenMealsInDb_ReturnTheMaxOfUpdatedAt() {
        // arrange
        // act
        val maxUpdatedAt: String = SUT.getMaxUpdated()
        //println(SUT.getByParentId("0").blockingFirst().size)
        // assert
        assertThat(maxUpdatedAt, CoreMatchers.equalTo("2022-01-01 00:00:00"))
    }

    @Test
    fun getMaxUpdatedAt_WhenNoMealsInDb_ReturnTheDefaultMaxUpdatedAt() {
        // arrange
        db.clearAllTables()
        // act
        val maxUpdatedAt: String = SUT.getMaxUpdated()
        //println(SUT.getByParentId("0").blockingFirst().size)
        // assert
        assertThat(maxUpdatedAt, CoreMatchers.equalTo("1970-01-01 00:00:01"))
    }

    @Test
    fun checkExists_WithExistingMeal_ReturnTrue() {
        // arrange
        // act
        val isExists: Boolean =  SUT.checkExists(mealsTestData[0])
        // assert
        assertTrue(isExists)
    }

    @Test
    fun checkExists_WithNonExistingMeal_ReturnFalse() {
        // arrange
        val mealData = MealData("id15", "name1", "detail1", "image_url", "id2",
            "2020-01-01 00:00:00", "1", "favorite")
        // act
        val isExists: Boolean =  SUT.checkExists(mealData)
        // assert
        assertFalse(isExists)
    }

    @Test
    fun upsert_WithNonExistingMeal_CallInsertAndNotCallUpdate() {
        // arrange
        val mealData = MealData("id15", "name1", "detail1", "image_url", "id2",
            "2020-01-01 00:00:00", "1", "favorite")
        // act
        SUT.upsert(mealData)
        // assert
        verify(SUT).insert(mealData)
        verify(SUT, never()).update(any<MealData>())
    }

    @Test
    fun upsert_WithExistingMeal_CallUpdateAndNotCallInsert() {
        // arrange
        val mealData = MealData("id2", "name2", "detail2", "image_url", "id1",
            "2021-01-01 00:00:00", "1", "favorite")
        // act
        SUT.upsert(mealData)
        // assert
        verify(SUT).update(mealData)
        verify(SUT, never()).insert(any<MealData>())
    }

}