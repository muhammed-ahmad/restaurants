package com.falcon.restaurants.presentation.common.di.presentation
import com.falcon.restaurants.presentation.view.restaurant.RestaurantsActivity
import com.falcon.restaurants.presentation.view.meal.MealsActivity
import com.falcon.restaurants.presentation.view.mealdetail.MealDetailsActivity
import com.falcon.restaurants.presentation.view.splash.SplashActivity
import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [PresentationModule::class])
interface PresentationComponent {

    fun inject(activity: SplashActivity)
    fun inject(activity: MealsActivity)
    fun inject(activity: RestaurantsActivity)
    fun inject(activity: MealDetailsActivity)

}
