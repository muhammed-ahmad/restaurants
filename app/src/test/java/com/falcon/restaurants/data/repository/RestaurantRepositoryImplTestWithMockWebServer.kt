package com.falcon.restaurants.data.repository

import com.falcon.restaurants.data.MockWebServerUtil
import com.falcon.restaurants.data.db.dao.RestaurantDataDao
import com.falcon.restaurants.data.mapper.RestaurantDataMapper
import com.falcon.restaurants.data.net.RetrofitInterface
import com.falcon.restaurants.domain.model.Restaurant
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RestaurantRepositoryImplTestWithMockWebServer{

    lateinit var SUT: RestaurantRepositoryImpl
    @Mock lateinit var restaurantDataDao: RestaurantDataDao
    lateinit var retrofitInterface: RetrofitInterface
    @Spy  lateinit var restaurantDataMapper: RestaurantDataMapper

    private lateinit var mockWebServer: MockWebServer
    val restaurants = MockWebServerUtil.createRestaurants()

    @Before
    fun setUp(){
        mockWebServer = MockWebServer()
        retrofitInterface = MockWebServerUtil.getRetrofitInterfaceMock(mockWebServer)
        SUT = RestaurantRepositoryImpl(restaurantDataDao, retrofitInterface, restaurantDataMapper)
    }

    @After
    fun shutdownServer() {
        mockWebServer.shutdown()
    }

    @Test
    fun fetchRestaurants_WhenCorrectResponse_ReturnCorrectParsedList() {
        // arrange
        MockWebServerUtil.setMockWebServerResponse(mockWebServer, MockWebServerUtil.getSuccessRestaurantsResponse())
        // act
        val testObserver = SUT.fetchRestaurants().test()
        // assert
        testObserver.assertResult(restaurants)
        testObserver.assertComplete()
        testObserver.assertValue { restaurants -> restaurants[0].id.equals("id1") }
        testObserver.assertValue { restaurants -> restaurants.size == 3 }
        testObserver.assertValue { restaurants -> restaurants is List<Restaurant> }
        testObserver.assertValueCount(1)
    }

    @Test
    fun fetchRestaurants_WhenEmptyResponse_ReturnEmptyList(){
        // arrange
        MockWebServerUtil.setMockWebServerResponse(mockWebServer, MockWebServerUtil.getEmptyRestaurantsResponse())
        // act
        val testObserver = SUT.fetchRestaurants().test()
        // assert
        testObserver.assertComplete()
        testObserver.assertValue { restaurants -> restaurants.size == 0 }
        testObserver.assertValue { restaurants -> restaurants is List<Restaurant> }
    }

}