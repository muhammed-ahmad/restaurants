package com.falcon.restaurants.presentation.screens.common
import androidx.appcompat.app.AppCompatActivity
import com.falcon.restaurants.presentation.common.di.activity.ActivityScope
import com.falcon.restaurants.presentation.screens.restaurant.RestaurantsActivity
import com.falcon.restaurants.presentation.screens.meal.MealsActivity
import com.falcon.restaurants.presentation.screens.mealdetail.MealDetailsActivity
import javax.inject.Inject

@ActivityScope
class ScreensNavigator @Inject constructor( val fromActivity: AppCompatActivity){

    fun toRestaurantsActivity(restaurantId: String){
        RestaurantsActivity.start(fromActivity, restaurantId)
    }

    fun toMealsActivity(restaurantId: String) {
        MealsActivity.start(fromActivity, restaurantId)
    }

    fun toMealDetailsActivity(mealId: String){
        MealDetailsActivity.start(fromActivity, mealId)
    }
}
