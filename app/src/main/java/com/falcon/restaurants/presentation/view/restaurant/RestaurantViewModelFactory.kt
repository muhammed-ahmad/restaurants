package com.falcon.restaurants.presentation.view.restaurant

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.falcon.models.presentation.mapper.RestaurantMapper
import com.falcon.restaurants.domain.interactor.restaurant.FetchAndUpsertRestaurantsUseCase
import com.falcon.restaurants.domain.interactor.restaurant.GetRestaurantsByParentIdUseCase
import com.falcon.restaurants.domain.interactor.restaurant.IsRestaurantHasChildrenUseCase

class RestaurantViewModelFactory(
    var application: Application,
    var fetchAndUpsertRestaurantsUseCase: FetchAndUpsertRestaurantsUseCase,
    val getRestaurantsByParentIdUseCase: GetRestaurantsByParentIdUseCase,
    val isRestaurantHasChildrenUseCase: IsRestaurantHasChildrenUseCase,
    var restaurantMapper: RestaurantMapper

) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RestaurantViewModel(
            application,
            fetchAndUpsertRestaurantsUseCase,
            getRestaurantsByParentIdUseCase,
            isRestaurantHasChildrenUseCase,
            restaurantMapper
        ) as T
    }
}