package com.falcon.restaurants.screens.common;

import androidx.appcompat.app.AppCompatActivity;
import com.falcon.restaurants.common.di.activity.ActivityScope;
import com.falcon.restaurants.screens.restaurant.RestaurantsActivity;
import com.falcon.restaurants.screens.meal.MealsActivity;
import com.falcon.restaurants.screens.mealdetail.MealDetailsActivity;

import javax.inject.Inject;

@ActivityScope
public class ScreensNavigator {

    private AppCompatActivity fromActivity;

    @Inject
    public ScreensNavigator(AppCompatActivity fromActivity) {
        this.fromActivity = fromActivity;
    }

    public void toCategoriesActivity(String restaurantId){
        RestaurantsActivity.start(fromActivity, restaurantId);
    }

    public void toMealsActivity(String restaurantId) {
        MealsActivity.start(fromActivity, restaurantId);
    }

    public void toMealDetailsActivity(String mealId){
        MealDetailsActivity.start(fromActivity, mealId);
    }
}
