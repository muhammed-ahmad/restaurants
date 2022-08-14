package com.falcon.restaurants.domain.interactor.restaurant

import com.falcon.restaurants.domain.interactor.DomainTestData
import com.falcon.restaurants.domain.model.Restaurant
import com.falcon.restaurants.domain.repository.RestaurantRepository
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchRestaurantsUseCaseTest {

    lateinit var SUT: FetchRestaurantsUseCase
    @Mock lateinit var restaurantRepository: RestaurantRepository
    val RESTAURANTS: MutableList<Restaurant> = DomainTestData.createRestaurants()

    @Before
    fun setUp(){
        SUT = FetchRestaurantsUseCase(restaurantRepository)
    }

    @Test
    fun execute_withNonEmptyListInRepo_returnNonEmptyList(){
        // arrange
        Mockito.`when`(restaurantRepository.fetchRestaurants()).thenReturn(Single.just(RESTAURANTS))
        // act
        val testObserver: TestObserver<List<Restaurant>> = SUT.execute().test()
        // assert
        testObserver.assertComplete()
        testObserver.assertValue(RESTAURANTS)
    }

    @Test
    fun execute_withEmptyListInRepo_returnEmptyList(){
        // arrange
        val restaurants: MutableList<Restaurant> = mutableListOf()
        Mockito.`when`(restaurantRepository.fetchRestaurants()).thenReturn(Single.just(restaurants))
        // act
        val testObserver: TestObserver<List<Restaurant>> = SUT.execute().test()
        // assert
        testObserver.assertComplete()
        testObserver.assertValue(restaurants)
    }

}