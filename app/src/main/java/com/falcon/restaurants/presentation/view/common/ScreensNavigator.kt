package com.falcon.restaurants.presentation.view.common
import androidx.appcompat.app.AppCompatActivity
import com.falcon.restaurants.presentation.di.activity.ActivityScope
import com.falcon.restaurants.presentation.view.restaurant.RestaurantsActivity
import com.falcon.restaurants.presentation.view.meal.MealsActivity
import com.falcon.restaurants.presentation.view.mealdetail.MealDetailsActivity
import javax.inject.Inject

@ActivityScope
class ScreensNavigator @Inject constructor( val fromActivity: AppCompatActivity){

    fun toRestaurantsActivity(){
        RestaurantsActivity.start(fromActivity)
    }

    fun toMealsActivity(restaurantId: String) {
        MealsActivity.start(fromActivity, restaurantId)
    }

    fun toMealDetailsActivity(mealId: String){
        MealDetailsActivity.start(fromActivity, mealId)
    }
}
