package com.falcon.restaurants.data.repository

import com.falcon.restaurants.data.DataTestData
import com.falcon.restaurants.data.db.dao.RestaurantDataDao
import com.falcon.restaurants.data.mapper.RestaurantDataMapper
import com.falcon.restaurants.data.net.RetrofitInterface
import com.falcon.restaurants.domain.interactor.DomainTestData
import com.falcon.restaurants.domain.model.Restaurant
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RestaurantRepositoryImplTest{

    lateinit var SUT: RestaurantRepositoryImpl
    @Mock lateinit var restaurantDataDao: RestaurantDataDao
    @Mock lateinit var retrofitInterface: RetrofitInterface
    @Spy  lateinit var restaurantDataMapper: RestaurantDataMapper

    val restaurantDatas = DataTestData.createRestaurantDatas()
    val restaurants = DomainTestData.createRestaurants()
    val restaurantDtos = DataTestData.createRestaurantDtos()

    @Before
    fun setUp(){
        SUT = RestaurantRepositoryImpl(restaurantDataDao, retrofitInterface, restaurantDataMapper)
    }

    @Test
    fun getRestaurants_WhenNonEmptyListInDb_ReturnNonEmptyList(){
        // arrange
        Mockito.`when`(restaurantDataDao.getRestaurants()).thenReturn(Observable.just(restaurantDatas))
        // act
        val testObserver: TestObserver<List<Restaurant>> = SUT.getRestaurants().test()
        // assert
        testObserver.assertComplete()
        testObserver.assertValue { restaurants -> restaurants[2].id.equals("id3") }
        testObserver.assertValue { restaurants -> restaurants.size == 3 }
        testObserver.assertValue { restaurants -> restaurants is List<Restaurant> }
        testObserver.assertValueCount(1)
    }

    @Test
    fun execute_WhenEmptyListInDb_ReturnEmptyList(){
        // arrange
        Mockito.`when`(restaurantDataDao.getRestaurants()).thenReturn(Observable.just(mutableListOf()))
        // act
        val testObserver: TestObserver<List<Restaurant>> = SUT.getRestaurants().test()
        // assert
        testObserver.assertComplete()
        testObserver.assertValue { restaurants -> restaurants.size == 0 }
        testObserver.assertValue { restaurants -> restaurants is List<Restaurant> }
    }

    @Test
    fun upsert_WhenUpsertListOfRestaurants_ReturnCompletable(){
        // arrange
        // act
        val testObserver = SUT.upsert(restaurants).test()
        // assert
        testObserver.assertComplete()
    }

    @Test
    fun fetchRestaurants_WhenNotUpdated_ReturnNonEmptyList(){
        // arrange
        val maxUpdatedAt = "1970-01-01 00:00:01"
        Mockito.`when`(restaurantDataDao.getMaxUpdated()).thenReturn(maxUpdatedAt)
        Mockito.`when`(retrofitInterface.fetchRestaurantDtos(maxUpdatedAt)).thenReturn(Single.just(restaurantDtos))
        // act
        val testObserver: TestObserver<List<Restaurant>> = SUT.fetchRestaurants().test()
        // assert
        testObserver.assertComplete()
        testObserver.assertValue { restaurants -> restaurants[0].id.equals("id1") }
        testObserver.assertValue { restaurants -> restaurants.size == 3 }
        testObserver.assertValue { restaurants -> restaurants is List<Restaurant> }
        testObserver.assertValueCount(1)
    }

    @Test
    fun fetchRestaurants_WhenUpdated_ReturnEmptyList(){
        // arrange
        val maxUpdatedAt = "2022-08-01 00:00:01"
        Mockito.`when`(restaurantDataDao.getMaxUpdated()).thenReturn(maxUpdatedAt)
        Mockito.`when`(retrofitInterface.fetchRestaurantDtos(maxUpdatedAt)).thenReturn(Single.just(mutableListOf()))
        // act
        val testObserver: TestObserver<List<Restaurant>> = SUT.fetchRestaurants().test()
        // assert
        testObserver.assertComplete()
        testObserver.assertValue { restaurants -> restaurants.size == 0 }
        testObserver.assertValue { restaurants -> restaurants is List<Restaurant> }
    }
}