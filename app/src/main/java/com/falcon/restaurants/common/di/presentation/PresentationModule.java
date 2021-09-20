package com.falcon.restaurants.common.di.presentation;

import android.app.Application;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import com.falcon.restaurants.room.RoomDB;
import com.falcon.restaurants.room.restaurant.RestaurantDao;
import com.falcon.restaurants.room.meal.MealDao;
import com.falcon.restaurants.screens.restaurant.FetchRestaurantsUseCase;
import com.falcon.restaurants.screens.restaurant.RestaurantViewModel;
import com.falcon.restaurants.screens.restaurant.RestaurantViewModelFactory;
import com.falcon.restaurants.screens.meal.FetchMealsUseCase;
import com.falcon.restaurants.screens.meal.MealViewModel;
import com.falcon.restaurants.screens.meal.MealViewModelFactory;
import com.falcon.restaurants.screens.splash.SplashViewModel;
import com.falcon.restaurants.screens.splash.SplashViewModelFactory;
import dagger.Module;
import dagger.Provides;

@Module
public class PresentationModule {

    // daos
    @Provides
    public MealDao getMealDao(RoomDB roomDB) {
        return roomDB.mealDao();
    }

    @Provides
    public RestaurantDao getRestaurantDao(RoomDB roomDB) {
        return roomDB.restaurantDao();
    }

    // view models
    @Provides
    public RestaurantViewModel getRestaurantViewModel(AppCompatActivity fragmentActivity, Application application,
                                                      FetchRestaurantsUseCase fetchRestaurantsUseCase){
        return ViewModelProviders.of(
                fragmentActivity,
                new RestaurantViewModelFactory(application, fetchRestaurantsUseCase)
        ).get(RestaurantViewModel.class);
    }

    @Provides
    public MealViewModel getMealViewModel(AppCompatActivity fragmentActivity,
                                          Application application,
                                          FetchMealsUseCase fetchMealsUseCase){
        return ViewModelProviders.of(
                fragmentActivity,
                new MealViewModelFactory(application, fetchMealsUseCase)
        ).get(MealViewModel.class);
    }

    @Provides
    public SplashViewModel getSplashViewModel (AppCompatActivity fragmentActivity,
                                               Application application,
                                               RestaurantViewModel restaurantViewModel,
                                               MealViewModel mealViewModel){
        return ViewModelProviders.of(
                fragmentActivity,
                new SplashViewModelFactory(application, restaurantViewModel, mealViewModel)
        ).get(SplashViewModel.class);
    }
}
