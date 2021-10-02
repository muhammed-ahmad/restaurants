package com.falcon.restaurants.screens.restaurant

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RestaurantViewModelFactory(
    var application: Application,
    var fetchRestaurantsUseCase: FetchRestaurantsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RestaurantViewModel(application, fetchRestaurantsUseCase) as T
    }
}