package com.falcon.restaurants.presentation.view.restaurant

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.falcon.restaurants.domain.interactor.restaurant.FetchAndUpsertRestaurantsUseCase
import com.falcon.restaurants.domain.interactor.restaurant.GetRestaurantsUseCase
import com.falcon.restaurants.domain.model.Restaurant
import com.falcon.restaurants.presentation.PresentationTestData
import com.falcon.restaurants.presentation.RxImmediateSchedulerRule
import io.reactivex.Observable
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.never


@RunWith(MockitoJUnitRunner::class)
class RestaurantViewModelTest {

    lateinit var SUT: RestaurantViewModel
    @Mock lateinit var application: Application
    @Mock lateinit var fetchAndUpsertRestaurantsUseCase: FetchAndUpsertRestaurantsUseCase
    @Mock lateinit var getRestaurantsUseCase: GetRestaurantsUseCase

    @Mock lateinit var lifecycleObserver: Observer<List<Restaurant>>
    @Captor lateinit var captor: ArgumentCaptor<List<Restaurant>>

    val restaurants = PresentationTestData.createRestaurants()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        SUT = RestaurantViewModel(application, fetchAndUpsertRestaurantsUseCase, getRestaurantsUseCase)
    }

    @Test
    fun getRestaurants_WhenNonEmptyData_ReturnLiveDataOfRestaurantsList(){
        // arrange
        Mockito.`when`(getRestaurantsUseCase.execute()).thenReturn(Observable.just(restaurants))
        // act
        SUT.getRestaurants().observeForever(lifecycleObserver)
        // assert
        Mockito.verify(lifecycleObserver).onChanged(any())
        captor.run {
            Mockito.verify(lifecycleObserver, Mockito.times(1)).onChanged(capture())
            assertEquals(restaurants, value)
        }
    }

    @Test
    fun getRestaurants_WhenEmptyData_ThenLiveDataNotObservedOfEmptyRestaurantsList(){
        Mockito.`when`(getRestaurantsUseCase.execute()).thenReturn(Observable.empty())
        SUT.getRestaurants().observeForever(lifecycleObserver)
        Mockito.verify(lifecycleObserver, never()).onChanged(any())
    }

}