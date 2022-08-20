package com.falcon.restaurants.data.repository

import com.falcon.restaurants.data.MockWebServerUtil
import com.falcon.restaurants.data.db.dao.MealDataDao
import com.falcon.restaurants.data.mapper.MealDataMapper
import com.falcon.restaurants.data.net.RetrofitInterface
import com.falcon.restaurants.domain.model.Meal
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)

class MealRepositoryImplTestWithMockWebServer {

    lateinit var SUT: MealRepositoryImpl
    @Mock lateinit var mealDataDao: MealDataDao
    lateinit var retrofitInterface: RetrofitInterface
    @Spy lateinit var mealDataMapper: MealDataMapper

    private lateinit var mockWebServer: MockWebServer
    val meals = MockWebServerUtil.createMeals()

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        retrofitInterface = MockWebServerUtil.getRetrofitInterfaceMock(mockWebServer)
        SUT = MealRepositoryImpl(mealDataDao, retrofitInterface, mealDataMapper)
    }

    @After
    fun shutdownServer() {
        mockWebServer.shutdown()
    }

    @Test
    fun fetchMeals_WhenCorrectResponse_ReturnCorrectParsedList() {
        // arrange
        MockWebServerUtil.setMockWebServerResponse(mockWebServer, MockWebServerUtil.getSuccessMealsResponse())
        // act
        val testObserver = SUT.fetchMeals().test()
        // assert
        testObserver.assertResult(meals)
        testObserver.assertComplete()
        testObserver.assertValue { meals -> meals[0].id.equals("id1") }
        testObserver.assertValue { meals -> meals.size == 3 }
        testObserver.assertValue { meals -> meals is List<Meal> }
        testObserver.assertValueCount(1)
    }

    @Test
    fun fetchMeals_WhenEmptyResponse_ReturnEmptyList(){
        // arrange
        MockWebServerUtil.setMockWebServerResponse(mockWebServer, MockWebServerUtil.getEmptyMealsResponse())
        // act
        val testObserver = SUT.fetchMeals().test()
        // assert
        testObserver.assertComplete()
        testObserver.assertValue { meals -> meals.size == 0 }
        testObserver.assertValue { meals -> meals is List<Meal> }
    }


}