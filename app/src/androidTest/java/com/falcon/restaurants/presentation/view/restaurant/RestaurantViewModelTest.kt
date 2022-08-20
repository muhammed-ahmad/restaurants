package com.falcon.restaurants.presentation.view.restaurant

import android.app.Application
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.falcon.restaurants.domain.interactor.restaurant.FetchAndUpsertRestaurantsUseCase
import com.falcon.restaurants.domain.interactor.restaurant.GetRestaurantsUseCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RestaurantViewModelTest {

    lateinit var SUT: RestaurantViewModel

    lateinit var application: Application
    lateinit var fetchAndUpsertRestaurantsUseCase: FetchAndUpsertRestaurantsUseCase
    lateinit var getRestaurantsUseCase: GetRestaurantsUseCase

    @Before
    fun setUp() {
        SUT = RestaurantViewModel(application, fetchAndUpsertRestaurantsUseCase, getRestaurantsUseCase)
    }

}