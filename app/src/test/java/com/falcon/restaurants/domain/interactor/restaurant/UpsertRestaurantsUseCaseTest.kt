package com.falcon.restaurants.domain.interactor.restaurant

import com.falcon.restaurants.domain.interactor.DomainTestData
import com.falcon.restaurants.domain.model.Restaurant
import com.falcon.restaurants.domain.repository.RestaurantRepository
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UpsertRestaurantsUseCaseTest{

    lateinit var SUT: UpsertRestaurantsUseCase
    @Mock lateinit var restaurantRepository: RestaurantRepository
    val RESTAURANTS: MutableList<Restaurant> = DomainTestData.createRestaurants()

    @Before
    fun setUp(){
        SUT = UpsertRestaurantsUseCase(restaurantRepository)
    }

    @Test
    fun execute_upsertListOfRestaurant_returnCompletable(){
        // arrange
        Mockito.`when`(restaurantRepository.upsert(RESTAURANTS)).thenReturn(Completable.complete())
        // act
        val testObserver = SUT.execute(RESTAURANTS).test()
        // assert
        testObserver.assertComplete()
        Mockito.verify(restaurantRepository, Mockito.times(1)).upsert(RESTAURANTS)
    }

}