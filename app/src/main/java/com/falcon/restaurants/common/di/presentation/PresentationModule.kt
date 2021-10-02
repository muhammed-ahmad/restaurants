package com.falcon.restaurants.common.di.presentation
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.falcon.restaurants.room.RoomDB
import com.falcon.restaurants.room.restaurant.RestaurantDao
import com.falcon.restaurants.room.meal.MealDao
import com.falcon.restaurants.screens.restaurant.FetchRestaurantsUseCase
import com.falcon.restaurants.screens.restaurant.RestaurantViewModel
import com.falcon.restaurants.screens.restaurant.RestaurantViewModelFactory
import com.falcon.restaurants.screens.meal.FetchMealsUseCase
import com.falcon.restaurants.screens.meal.MealViewModel
import com.falcon.restaurants.screens.meal.MealViewModelFactory
import com.falcon.restaurants.screens.splash.SplashViewModel
import com.falcon.restaurants.screens.splash.SplashViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    // daos
    @Provides
    fun getMealDao(roomDB: RoomDB): MealDao {
        return roomDB.mealDao()
    }

    @Provides
    fun getRestaurantDao(roomDB: RoomDB): RestaurantDao {
        return roomDB.restaurantDao()
    }

    // view models
    @Provides
    fun getRestaurantViewModel(fragmentActivity: AppCompatActivity,
                               application: Application,
                               fetchRestaurantsUseCase: FetchRestaurantsUseCase
                               ): RestaurantViewModel{

        return ViewModelProviders.of(
                fragmentActivity,
                RestaurantViewModelFactory(application, fetchRestaurantsUseCase)
        ).get(RestaurantViewModel::class.java)
    }

    @Provides
    fun getMealViewModel(fragmentActivity: AppCompatActivity,
                         application: Application,
                         fetchMealsUseCase: FetchMealsUseCase
                         ): MealViewModel{

        return ViewModelProviders.of(
                fragmentActivity,
                MealViewModelFactory(application, fetchMealsUseCase)
        ).get(MealViewModel::class.java)
    }

    @Provides
    fun getSplashViewModel (fragmentActivity: AppCompatActivity,
                            application: Application,
                            restaurantViewModel: RestaurantViewModel,
                            mealViewModel: MealViewModel
                            ) : SplashViewModel{

        return ViewModelProviders.of(
                fragmentActivity,
                SplashViewModelFactory(application, restaurantViewModel, mealViewModel)
        ).get(SplashViewModel::class.java)
    }
}
