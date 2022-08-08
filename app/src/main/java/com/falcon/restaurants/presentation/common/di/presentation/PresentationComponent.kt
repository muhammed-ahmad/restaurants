package com.falcon.restaurants.presentation.common.di.presentation
import com.falcon.restaurants.presentation.screens.restaurant.RestaurantsActivity
import com.falcon.restaurants.presentation.screens.meal.MealsActivity
import com.falcon.restaurants.presentation.screens.mealdetail.MealDetailsActivity
import com.falcon.restaurants.presentation.screens.splash.SplashActivity
import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [PresentationModule::class])
interface PresentationComponent {

    fun inject(activity: SplashActivity)
    fun inject(activity: MealsActivity)
    fun inject(activity: RestaurantsActivity)
    fun inject(activity: MealDetailsActivity)

}
