package com.falcon.restaurants.presentation.screens.restaurant

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.falcon.models.presentation.mapper.RestaurantMapper
import com.falcon.restaurants.domain.interactor.FetchRestaurantsUseCase

class RestaurantViewModelFactory(
    var application: Application,
    var fetchRestaurantsUseCase: FetchRestaurantsUseCase,
    var restaurantMapper: RestaurantMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RestaurantViewModel(application, fetchRestaurantsUseCase, restaurantMapper) as T
    }
}