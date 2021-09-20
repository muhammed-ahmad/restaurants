package com.falcon.restaurants.common.di.presentation;

import com.falcon.restaurants.screens.restaurant.RestaurantsActivity;
import com.falcon.restaurants.screens.meal.MealsActivity;
import com.falcon.restaurants.screens.mealdetail.MealDetailsActivity;
import com.falcon.restaurants.screens.splash.SplashActivity;
import dagger.Subcomponent;

@PresentationScope
@Subcomponent(modules = {PresentationModule.class})
public interface PresentationComponent {

    void inject(SplashActivity activity);
    void inject(MealsActivity activity);
    void inject(RestaurantsActivity activity);
    void inject(MealDetailsActivity activity);

}
