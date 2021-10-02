package com.falcon.restaurants.screens.splash

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.falcon.restaurants.screens.restaurant.RestaurantViewModel
import com.falcon.restaurants.screens.meal.MealViewModel

class SplashViewModelFactory(
    var application: Application,
    var restaurantViewModel: RestaurantViewModel,
    var mealViewModel: MealViewModel
): ViewModelProvider.Factory {

    override fun <T :ViewModel> create(modelClass:  Class<T>) : T {
        return SplashViewModel(application, restaurantViewModel, mealViewModel) as T
    }
}