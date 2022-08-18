package com.falcon.restaurants.presentation.view.meal
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.falcon.restaurants.domain.interactor.meal.FetchAndUpsertMealUseCase
import com.falcon.restaurants.domain.interactor.meal.GetMealByIdUseCase
import com.falcon.restaurants.domain.interactor.meal.GetMealsByRestaurantIdUseCase

class MealViewModelFactory(
    var application: Application,
    var fetchAndUpsertMealUseCase: FetchAndUpsertMealUseCase,
    val getMealsByRestaurantIdUseCase: GetMealsByRestaurantIdUseCase,
    val getMealByIdUseCase: GetMealByIdUseCase

) : ViewModelProvider.Factory{
     override fun <T : ViewModel?> create(modelClass: Class<T>): T{
        return MealViewModel(
            application,
            fetchAndUpsertMealUseCase,
            getMealsByRestaurantIdUseCase,
            getMealByIdUseCase
        ) as T
    }
}