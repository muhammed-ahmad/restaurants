package com.falcon.restaurants.screens.common;

import androidx.appcompat.app.AppCompatActivity;
import com.falcon.restaurants.common.di.activity.ActivityScope;
import com.falcon.restaurants.screens.restaurant.RestaurantsActivity;
import com.falcon.restaurants.screens.meal.MealsActivity;
import com.falcon.restaurants.screens.mealdetail.MealDetailsActivity;

import javax.inject.Inject;

@ActivityScope
class ScreensNavigator @Inject constructor( val fromActivity: AppCompatActivity){

    fun toCategoriesActivity(restaurantId: String){
        RestaurantsActivity.start(fromActivity, restaurantId)
    }

    fun toMealsActivity(restaurantId: String) {
        MealsActivity.start(fromActivity, restaurantId)
    }

    fun toMealDetailsActivity(mealId: String){
        MealDetailsActivity.start(fromActivity, mealId)
    }
}
