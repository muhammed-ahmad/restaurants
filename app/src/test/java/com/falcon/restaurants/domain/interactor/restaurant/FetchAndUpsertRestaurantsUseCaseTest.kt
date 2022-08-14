package com.falcon.restaurants.domain.interactor.restaurant

import com.falcon.restaurants.domain.interactor.DomainTestData
import com.falcon.restaurants.domain.model.Restaurant
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchAndUpsertRestaurantsUseCaseTest {

    lateinit var SUT: FetchAndUpsertRestaurantsUseCase
    @Mock lateinit var fetchRestaurantsUseCase: FetchRestaurantsUseCase
    @Mock lateinit var upsertRestaurantsUseCase: UpsertRestaurantsUseCase
    val RESTAURANTNETS: MutableList<Restaurant> = DomainTestData.createRestaurants()

    @Before
    fun setUp(){
        SUT = FetchAndUpsertRestaurantsUseCase(
            fetchRestaurantsUseCase,
            upsertRestaurantsUseCase
        )
    }

    @Test
    fun execute_fetchRestaurantsSuccess_upsertRestaurants() {
        // Arrange
        Mockito.`when`(fetchRestaurantsUseCase.execute()).thenReturn(Single.just(RESTAURANTNETS))
        Mockito.`when`(upsertRestaurantsUseCase.execute(RESTAURANTNETS)).thenReturn(Completable.complete())
        // act
        val testObserver: TestObserver<Void> = SUT.execute().test()
        // assert
        testObserver.assertComplete()
        Mockito.verify(upsertRestaurantsUseCase, Mockito.times(1)).execute(RESTAURANTNETS)
    }

    @Test
    fun execute_fetchRestaurantsFailed_upsertRestaurantsNotCalled() {
        // Arrange
        Mockito.`when`(fetchRestaurantsUseCase.execute()).thenReturn(null)
        // act
        val testObserver: TestObserver<Void> = SUT.execute().test()
        // assert
        testObserver.assertNotComplete()
        testObserver.assertError(Throwable::class.java)
        Mockito.verify(upsertRestaurantsUseCase, Mockito.never()).execute(ArrayList())
    }

}