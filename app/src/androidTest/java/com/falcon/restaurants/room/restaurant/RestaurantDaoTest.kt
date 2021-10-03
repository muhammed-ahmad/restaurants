package com.falcon.restaurants.room.restaurant

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.falcon.restaurants.room.RoomDB
import com.falcon.restaurants.testdata.RestaurantTestDataInstru
import com.falcon.restaurants.utils.Logger
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.*
import org.mockito.Mockito
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.any

@RunWith(AndroidJUnit4::class)
class RestaurantDaoTest {

    companion object {
        const val TAG: String = "RestaurantDaoTest"
        val RESTAURANTS: List<Restaurant> = RestaurantTestDataInstru.createRestaurantsForRoom() as List<Restaurant>
        lateinit var SUT: RestaurantDao
        lateinit var db: RoomDB

        @BeforeClass @JvmStatic
        fun setUp() {
            val context: Context = ApplicationProvider.getApplicationContext()
            db = Room.inMemoryDatabaseBuilder(
                context,
                RoomDB::class.java).allowMainThreadQueries().build()
            SUT = db.restaurantDao()
            SUT.upsert(RESTAURANTS)
        }

        @AfterClass @JvmStatic
        fun closeDb(){
            db.close()
        }
    }

    @Rule @JvmField
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun buildSpy() {
        SUT = Mockito.spy(db.restaurantDao())
    }

    @Test
    fun upsert() {
    }

    @Test
    fun getAll() {

        val testObserver: TestObserver<List<Restaurant>> = SUT.getByParentId("0").test()
        testObserver.assertValue({Restaurants -> Restaurants.size == 3})
        testObserver.assertValue {Restaurants -> Restaurants.get(0).id.equals("id1")}
        testObserver.assertValueCount(1)

        SUT.insert(Restaurant("id6", "0", "name6",  "image_url",
                "1", "1970-01-01 00:00:03"))
        SUT.insert(Restaurant("id7", "0", "name7",  "image_url",
                "1", "1970-01-01 00:00:03"))

        testObserver.assertValueCount(3)

        val values: List<List<Restaurant>> = testObserver.values()
        Logger.log( TAG,"getAll: " + values.get(values.size-1).size) // get the size of the last one

        testObserver.assertValueAt(0, {Restaurants -> Restaurants.size == 3})
        testObserver.assertValueAt(1, {Restaurants -> Restaurants.size == 4})
        testObserver.assertValueAt(2, {Restaurants -> Restaurants.size == 5})

    }

    @Test
    fun getMaxUpdatedAt() {
        val maxUpdatedAt: String = SUT.getMaxUpdated()
        assertThat(maxUpdatedAt, equalTo("1970-01-01 00:00:05"))
    }

    @Test
    fun checkExists() {
        val isExists: Boolean =  SUT.checkExists(RESTAURANTS.get(0))
        assertTrue(isExists)
    }

    @Test
    fun CheckNotExists() {
        val restaurant: Restaurant = Restaurant("id5", "0", "name5",
                "image_url", "1", "1970-01-01 00:00:03")
        val isExists: Boolean =  SUT.checkExists(restaurant)
        assertFalse(isExists)
    }

    @Test
    fun CheckUpsertWithInsert() {
        val restaurant: Restaurant = Restaurant("id8", "0", "name8",
                "image_url", "1", "1970-01-01 00:00:03")
        SUT.upsert(restaurant)
        verify(SUT).insert(restaurant)
        verify(SUT, never()).update(any<Restaurant>())
    }

    @Test
    fun CheckUpsertWithUpdate() {
        //Mockito.reset(SUT)
        val restaurant: Restaurant = Restaurant("id3", "0", "name3",
                "image_url", "1", "1970-01-01 00:00:03")
        SUT.upsert(restaurant)

        verify(SUT).update(restaurant)
        //verify(SUT).insert(restaurant)
        verify(SUT, never()).insert(any<Restaurant>())
        //verify(SUT).insert(any(Restaurant.class))
    }

}