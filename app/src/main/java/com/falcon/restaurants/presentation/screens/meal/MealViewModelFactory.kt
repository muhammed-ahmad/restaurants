package com.falcon.restaurants.presentation.screens.meal
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.falcon.restaurants.domain.interactor.FetchMealsUseCase
import com.falcon.restaurants.presentation.mapper.MealMapper

class MealViewModelFactory(
        var application: Application,
        var fetchMealsUseCase: FetchMealsUseCase,
        val mealMapper: MealMapper
) : ViewModelProvider.Factory{
     override fun <T : ViewModel?> create(modelClass: Class<T>): T{
        return MealViewModel(application, fetchMealsUseCase, mealMapper) as T
    }
}