//package com.falcon.restaurants.data.db.restaurant
//
//import android.content.Context
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.room.Room
//import androidx.test.core.app.ApplicationProvider
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.falcon.restaurants.data.db.RoomDB
//import com.falcon.restaurants.data.db.dao.RestaurantDataDao
//import com.falcon.restaurants.data.db.model.RestaurantData
//import com.falcon.restaurants.testdata.RestaurantTestDataInstrument
//import org.junit.runner.RunWith
//import io.reactivex.observers.TestObserver
//import org.hamcrest.CoreMatchers.equalTo
//import org.junit.*
//import org.junit.Assert.*
//import org.mockito.Mockito
//import org.mockito.kotlin.never
//import org.mockito.kotlin.verify
//import org.mockito.kotlin.any
//
//@RunWith(AndroidJUnit4::class)
//class RestaurantUiModelModelDaoTest {
//
//    companion object {
//        const val TAG: String = "RestaurantDaoTest"
//        val RESTAURANT_DATA: List<RestaurantData> = RestaurantTestDataInstrument.createRestaurants() as List<RestaurantData>
//        lateinit var SUT: RestaurantDataDao
//        lateinit var db: RoomDB
//
//        @BeforeClass @JvmStatic
//        fun setUp() {
//            val context: Context = ApplicationProvider.getApplicationContext()
//            db = Room.inMemoryDatabaseBuilder( context, RoomDB::class.java).allowMainThreadQueries().build()
//        }
//
//        @AfterClass @JvmStatic
//        fun closeDb(){
//            db.close()
//        }
//    }
//
//    @Rule @JvmField
//    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @Before
//    fun setUpForEachMethod() {
//        SUT = Mockito.spy(db.restaurantDao())
//        db.clearAllTables()
//        SUT.upsert(RESTAURANT_DATA)
//        Mockito.reset(SUT)
//    }
//
//    @Test
//    fun getByParentId_nonEmptyList_returnNonEmptyListHavingTheSameParentId() {
//        // arrange
//        // act
//        val testObserver: TestObserver<List<RestaurantData>> = SUT.getRestaurants("0").test()
//        // assert
//        testObserver.assertValue { restaurants -> restaurants.size == 3 }
//        testObserver.assertValue { restaurants -> restaurants[0].id.equals("id1") }
//        testObserver.assertValueCount(1)
//    }
//
//    @Test
//    fun getByParentId_nonEmptyListThenInsert_returnNonEmptyListHavingTheSameParentId() {
//        // arrange
//        // act
//        val testObserver: TestObserver<List<RestaurantData>> = SUT.getRestaurants("0").test()
//        SUT.insert(
//            RestaurantData("id6", "0", "name6",  "image_url",
//            "1", "1970-01-01 00:00:03")
//        )
//        SUT.insert(
//            RestaurantData("id7", "0", "name7",  "image_url",
//            "1", "1970-01-01 00:00:03")
//        )
//
//        // assert
//        testObserver.assertValueCount(3)
//
//        //val values: List<List<Restaurant>> = testObserver.values()
//        //Logger.log( TAG,"getAll: " + values[values.size-1].size) // get the size of the last one
//        testObserver.assertValueAt(0) { restaurants -> restaurants.size == 3 }
//        testObserver.assertValueAt(1) { restaurants -> restaurants.size == 4 }
//        testObserver.assertValueAt(2) { restaurants -> restaurants.size == 5 }
//    }
//
//    @Test
//    fun getMaxUpdatedAt_nonEmptyList_returnTheMaxOfUpdatedAt() {
//        // arrange
//        // act
//        val maxUpdatedAt: String = SUT.getMaxUpdated()
//        //println(SUT.getByParentId("0").blockingFirst().size)
//        // assert
//        assertThat(maxUpdatedAt, equalTo("1970-01-01 00:00:05"))
//    }
//
//    @Test
//    fun checkExists_existingRestaurant_returnTrue() {
//        // arrange
//        // act
//        val isExists: Boolean =  SUT.checkExists(RESTAURANT_DATA[0])
//        // assert
//        assertTrue(isExists)
//    }
//
//    @Test
//    fun checkExists_nonExistingRestaurant_returnFalse() {
//        // arrange
//        val restaurant = RestaurantData("id5", "0", "name5",
//                "image_url", "1", "1970-01-01 00:00:03")
//        // act
//        val isExists: Boolean =  SUT.checkExists(restaurant)
//        // assert
//        assertFalse(isExists)
//    }
//
//    @Test
//    fun upsert_nonExistingRestaurant_insertIsCalledAndUpdateNotCalled() {
//        // arrange
//        val restaurant = RestaurantData("id8", "0", "name8",
//                "image_url", "1", "1970-01-01 00:00:03")
//        // act
//        SUT.upsert(restaurant)
//        // assert
//        verify(SUT).insert(restaurant)
//        verify(SUT, never()).update(any<RestaurantData>())
//    }
//
//    @Test
//    fun upsert_existingRestaurant_updateIsCalledAndInsertNotCalled() {
//        // arrange
//        val restaurant = RestaurantData("id3", "0", "name3",
//                "image_url", "1", "1970-01-01 00:00:03")
//        // act
//        SUT.upsert(restaurant)
//        // assert
//        verify(SUT).update(restaurant)
//        verify(SUT, never()).insert(any<RestaurantData>())
//    }
//
//}