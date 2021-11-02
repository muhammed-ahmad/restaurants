package com.falcon.restaurants.screens.meal
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MealViewModelFactory(
        var application: Application,
        var fetchMealsUseCase: FetchMealsUseCase
) : ViewModelProvider.Factory{
     override fun <T : ViewModel?> create(modelClass: Class<T>): T{
        return MealViewModel(application, fetchMealsUseCase) as T
    }
}