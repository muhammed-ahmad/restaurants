package com.falcon.restaurants.screens.meal;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MealViewModelFactory implements ViewModelProvider.Factory {

    Application application ;
    FetchMealsUseCase fetchMealsUseCase;

    public MealViewModelFactory(Application application, FetchMealsUseCase fetchMealsUseCase) {
        this.application = application;
        this.fetchMealsUseCase = fetchMealsUseCase;
    }

    @androidx.annotation.NonNull
    @Override
    public <T extends ViewModel> T create(@androidx.annotation.NonNull Class<T> modelClass) {
        return (T) new MealViewModel(application, fetchMealsUseCase);
    }
}