package com.falcon.restaurants.data.db.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.falcon.restaurants.data.InstrumentedTestData
import com.falcon.restaurants.data.db.RoomDB
import com.falcon.restaurants.data.db.model.RestaurantData
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
class RestaurantDataDaoTest {

    companion object {
        const val TAG: String = "RestaurantDaoTest"
        val restaurantsTestData: List<RestaurantData> = InstrumentedTestData.createRestaurantDatas()
        lateinit var SUT: RestaurantDataDao
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
        SUT = Mockito.spy(db.restaurantDao())
        db.clearAllTables()
        SUT.upsert(restaurantsTestData)
        Mockito.reset(SUT)
    }

    @Test
    fun getRestaurants_WhenRestaurantsInDb_ReturnNonEmptyList() {
        // arrange
        // act
        val testObserver: TestObserver<List<RestaurantData>> = SUT.getRestaurants().test()
        // assert
        testObserver.assertValue { restaurants -> restaurants.size == 3 }
        testObserver.assertValue { restaurants -> restaurants[0].id.equals("id1") }
        testObserver.assertValueCount(1)
    }

    @Test
    fun getRestaurants_WhenRestaurantsInDbThenInsert_ReturnWithTheExtraItem() {
        // arrange
        // act
        val testObserver: TestObserver<List<RestaurantData>> = SUT.getRestaurants().test()
        SUT.insert(
            RestaurantData("id6", "0", "name6",  "image_url",
                "1", "1970-01-01 00:00:03")
        )
        SUT.insert(
            RestaurantData("id7", "0", "name7",  "image_url",
                "1", "1970-01-01 00:00:03")
        )

        // assert
        testObserver.assertValueCount(3)

        //val values: List<List<Restaurant>> = testObserver.values()
        //Logger.log( TAG,"getAll: " + values[values.size-1].size) // get the size of the last one
        testObserver.assertValueAt(0) { restaurants -> restaurants.size == 3 }
        testObserver.assertValueAt(1) { restaurants -> restaurants.size == 4 }
        testObserver.assertValueAt(2) { restaurants -> restaurants.size == 5 }
    }

    @Test
    fun getMaxUpdated_WhenRestaurantsInDb_ReturnTheMaxOfUpdatedAt() {
        // arrange
        // act
        val maxUpdatedAt: String = SUT.getMaxUpdated()
        //println(SUT.getByParentId("0").blockingFirst().size)
        // assert
        assertThat(maxUpdatedAt, CoreMatchers.equalTo("2022-01-01 00:00:00"))
    }

    @Test
    fun getMaxUpdated_WhenNoRestaurantsInDb_ReturnTheDefaultMaxUpdatedAt() {
        // arrange
        db.clearAllTables()
        // act
        val maxUpdatedAt: String = SUT.getMaxUpdated()
        //println(SUT.getByParentId("0").blockingFirst().size)
        // assert
        assertThat(maxUpdatedAt, CoreMatchers.equalTo("1970-01-01 00:00:01"))
    }

    @Test
    fun checkExists_WithExistingRestaurant_ReturnTrue() {
        // arrange
        // act
        val isExists: Boolean =  SUT.checkExists(restaurantsTestData[0])
        // assert
        assertTrue(isExists)
    }

    @Test
    fun checkExists_WithNonExistingRestaurant_ReturnFalse() {
        // arrange
        val restaurant = RestaurantData("id5", "0", "name5",
            "image_url", "1", "1970-01-01 00:00:01")
        // act
        val isExists: Boolean =  SUT.checkExists(restaurant)
        // assert
        assertFalse(isExists)
    }

    @Test
    fun upsert_WithNonExistingRestaurant_CallInsertAndNotCallUpdate() {
        // arrange
        val restaurant = RestaurantData("id8", "0", "name8",
            "image_url", "1", "1970-01-01 00:00:03")
        // act
        SUT.upsert(restaurant)
        // assert
        verify(SUT).insert(restaurant)
        verify(SUT, never()).update(any<RestaurantData>())
    }

    @Test
    fun upsert_WithExistingRestaurant_CallUpdateAndNotCallInsert() {
        // arrange
        val restaurant = RestaurantData("id3", "0", "name3",
            "image_url", "1", "1970-01-01 00:00:03")
        // act
        SUT.upsert(restaurant)
        // assert
        verify(SUT).update(restaurant)
        verify(SUT, never()).insert(any<RestaurantData>())
    }

}