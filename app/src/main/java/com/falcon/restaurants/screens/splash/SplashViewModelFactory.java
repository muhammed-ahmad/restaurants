package com.falcon.restaurants.screens.splash;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.falcon.restaurants.screens.restaurant.RestaurantViewModel;
import com.falcon.restaurants.screens.meal.MealViewModel;

public class SplashViewModelFactory implements ViewModelProvider.Factory {

    Application application ;
    RestaurantViewModel restaurantViewModel;
    MealViewModel mealViewModel;

    public SplashViewModelFactory(Application application,
                                  RestaurantViewModel restaurantViewModel,
                                  MealViewModel mealViewModel) {
        this.application = application;
        this.restaurantViewModel = restaurantViewModel;
        this.mealViewModel = mealViewModel;
    }

    @androidx.annotation.NonNull
    @Override
    public <T extends ViewModel> T create(@androidx.annotation.NonNull Class<T> modelClass) {
        return (T) new SplashViewModel(application, restaurantViewModel, mealViewModel);
    }
}