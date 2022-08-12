package com.falcon.restaurants.presentation.screens.restaurant

import com.falcon.restaurants.data.network.RetrofitInterface
import com.falcon.restaurants.data.network.restaurant.RestaurantDto
import com.falcon.restaurants.data.room.restaurant.RestaurantData
import com.falcon.restaurants.data.room.restaurant.RestaurantDataDao
import com.falcon.restaurants.domain.interactor.FetchRestaurantsUseCase
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

    val RESTAURANTNETS: MutableList<RestaurantDto> = RestaurantTestData.createRestaurantNets()
    lateinit var SUT: FetchRestaurantsUseCase

    lateinit var fetchRestaurantsEndPointTd: FetchRestaurantsEndPointTd
    @Mock lateinit var restaurantDataDaoMock: RestaurantDataDao
    @Mock lateinit var retrofitInterfaceMock: RetrofitInterface

    @Before
    fun setUp(){
        fetchRestaurantsEndPointTd = FetchRestaurantsEndPointTd(retrofitInterfaceMock)
        SUT = FetchRestaurantsUseCase(
                restaurantDataDaoMock,
                fetchRestaurantsEndPointTd
        )
        `when`(restaurantDataDaoMock.getMaxUpdated()).thenReturn("1970-01-01 00:00:01")
    }

    // fetch on success then upsert is called with the correct data and Observable emits upsert_completed
    @Test
    fun fetch_success_upsertWithTheCorrectData() {
        // Arrange
        success()
        // act
        val testObserver: TestObserver<String> = SUT.fetchAndUpsert().test()
        // assert
        verify(restaurantDataDaoMock, times(3)).upsert(any<RestaurantData>())
        testObserver.assertValue{ string -> string.equals("upsert_completed") }
    }

    // fetch on failed then upsert not called
    @Test
    fun fetch_failure_upsertNotCalled() {
        // Arrange
        failure()
        // act
        SUT.fetchAndUpsert().test()
        // assert
        verify(restaurantDataDaoMock, never()).upsert(ArrayList())
    }

    // fetch on failed then Observable emits error
    @Test
    fun fetch_failure_ObservableEmitsError() {
        // Arrange
        failure()
        // act
        val testObserver: TestObserver<String> = SUT.fetchAndUpsert().test()
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