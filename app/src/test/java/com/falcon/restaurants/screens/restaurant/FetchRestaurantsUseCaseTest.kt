package com.falcon.restaurants.screens.restaurant

import com.falcon.restaurants.network.RetrofitInterface
import com.falcon.restaurants.network.restaurant.FetchRestaurantsEndPoint
import com.falcon.restaurants.network.restaurant.RestaurantNet
import com.falcon.restaurants.room.restaurant.Restaurant
import com.falcon.restaurants.room.restaurant.RestaurantDao
import com.falcon.restaurants.testdata.RestaurantTestData
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class FetchRestaurantsUseCaseTest {

    val RESTAURANTNETS: MutableList<RestaurantNet> = RestaurantTestData.createRestaurantNets()
    lateinit var SUT: FetchRestaurantsUseCase

    lateinit var fetchRestaurantsEndPointTd: FetchRestaurantsEndPointTd
    @Mock lateinit var restaurantDaoMock: RestaurantDao
    @Mock lateinit var retrofitInterfaceMock: RetrofitInterface

    @Before
    fun setUp(){
        fetchRestaurantsEndPointTd = FetchRestaurantsEndPointTd(retrofitInterfaceMock)
        SUT = FetchRestaurantsUseCase(
                restaurantDaoMock,
                fetchRestaurantsEndPointTd
        )
        `when`(restaurantDaoMock.getMaxUpdated()).thenReturn("1970-01-01 00:00:01")
    }

    // fetch on success then upsert is called with the correct data and Observable emits upsert_completed
    @Test
    fun fetch_success_upsertWithTheCorrectData() {
        // Arrange
        success()
        // act
        val testObserver: TestObserver<String> = SUT.fetch().test()
        // assert
        verify(restaurantDaoMock, times(3)).upsert(any<Restaurant>())
        testObserver.assertValue{ string -> string.equals("upsert_completed") }
    }

    // fetch on failed then upsert not called
    @Test
    fun fetch_failure_upsertNotCalled() {
        // Arrange
        failure()
        // act
        SUT.fetch().test()
        // assert
        verify(restaurantDaoMock, never()).upsert(ArrayList())
    }

    // fetch on failed then Observable emits error
    @Test
    fun fetch_failure_ObservableEmitsError() {
        // Arrange
        failure()
        // act
        val testObserver: TestObserver<String> = SUT.fetch().test()
        // assert
        testObserver.assertError(Throwable::class.java)
    }

    // ******************* helper classes and methods ************************//

    fun success() {}

    fun failure() {
        fetchRestaurantsEndPointTd.failure = true 
    }

    inner class FetchRestaurantsEndPointTd(
        retrofitInterface: RetrofitInterface
    ): FetchRestaurantsEndPoint(retrofitInterface) {

        var failure: Boolean = false

        override fun fetch(maxUpdatedAt: String , listener: Listener) {
            if(failure){
                listener.onFetchFailed(Throwable())
            }else {
                listener.onFetchSuccess(RESTAURANTNETS)
            }
        }
    }
}