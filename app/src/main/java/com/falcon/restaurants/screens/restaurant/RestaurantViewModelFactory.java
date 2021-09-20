package com.falcon.restaurants.screens.restaurant;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RestaurantViewModelFactory implements ViewModelProvider.Factory {

    Application application ;
    FetchRestaurantsUseCase fetchRestaurantsUseCase;

    public RestaurantViewModelFactory(Application application, FetchRestaurantsUseCase fetchRestaurantsUseCase) {
        this.application = application;
        this.fetchRestaurantsUseCase = fetchRestaurantsUseCase;
    }

    @androidx.annotation.NonNull
    @Override
    public <T extends ViewModel> T create(@androidx.annotation.NonNull Class<T> modelClass) {
        return (T) new RestaurantViewModel(application, fetchRestaurantsUseCase);
    }
}