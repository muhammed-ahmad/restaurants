package com.falcon.restaurants.presentation.view.meal
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.falcon.restaurants.domain.interactor.meal.FetchAndUpsertMealUseCase
import com.falcon.restaurants.domain.interactor.meal.GetMealByIdUseCase
import com.falcon.restaurants.domain.interactor.meal.GetMealsByRestaurantIdUseCase
import com.falcon.restaurants.presentation.mapper.MealMapper

class MealViewModelFactory(
    var application: Application,
    var fetchAndUpsertMealUseCase: FetchAndUpsertMealUseCase,
    val getMealsByRestaurantIdUseCase: GetMealsByRestaurantIdUseCase,
    val getMealByIdUseCase: GetMealByIdUseCase,
    val mealMapper: MealMapper

) : ViewModelProvider.Factory{
     override fun <T : ViewModel?> create(modelClass: Class<T>): T{
        return MealViewModel(
            application,
            fetchAndUpsertMealUseCase,
            getMealsByRestaurantIdUseCase,
            getMealByIdUseCase,
            mealMapper
        ) as T
    }
}