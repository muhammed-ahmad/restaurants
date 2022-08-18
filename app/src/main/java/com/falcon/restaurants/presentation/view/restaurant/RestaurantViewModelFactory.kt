package com.falcon.restaurants.presentation.view.restaurant

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.falcon.restaurants.domain.interactor.restaurant.FetchAndUpsertRestaurantsUseCase
import com.falcon.restaurants.domain.interactor.restaurant.GetRestaurantsUseCase

class RestaurantViewModelFactory(
    var application: Application,
    var fetchAndUpsertRestaurantsUseCase: FetchAndUpsertRestaurantsUseCase,
    val getRestaurantsUseCase: GetRestaurantsUseCase

) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RestaurantViewModel(
            application,
            fetchAndUpsertRestaurantsUseCase,
            getRestaurantsUseCase
        ) as T
    }
}