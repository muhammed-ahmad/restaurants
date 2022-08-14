package com.falcon.restaurants.presentation.view.restaurant

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.falcon.models.presentation.mapper.RestaurantMapper
import com.falcon.restaurants.domain.interactor.restaurant.FetchAndUpsertRestaurantsUseCase
import com.falcon.restaurants.domain.interactor.restaurant.GetRestaurantsUseCase

class RestaurantViewModelFactory(
    var application: Application,
    var fetchAndUpsertRestaurantsUseCase: FetchAndUpsertRestaurantsUseCase,
    val getRestaurantsUseCase: GetRestaurantsUseCase,
    var restaurantMapper: RestaurantMapper

) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RestaurantViewModel(
            application,
            fetchAndUpsertRestaurantsUseCase,
            getRestaurantsUseCase,
            restaurantMapper
        ) as T
    }
}