package com.falcon.restaurants.presentation.common.di.presentation
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.falcon.models.presentation.mapper.RestaurantMapper
import com.falcon.restaurants.data.mapper.MealModelMapper
import com.falcon.restaurants.data.mapper.RestaurantModelMapper
import com.falcon.restaurants.data.network.meal.FetchMealsEndPoint
import com.falcon.restaurants.data.network.restaurant.FetchRestaurantsEndPoint
import com.falcon.restaurants.data.repository.MealRepositoryImpl
import com.falcon.restaurants.data.repository.RestaurantRepositoryImpl
import com.falcon.restaurants.data.room.RoomDB
import com.falcon.restaurants.data.room.meal.MealModelDao
import com.falcon.restaurants.data.room.restaurant.RestaurantModelDao
import com.falcon.restaurants.domain.interactor.FetchMealsUseCase
import com.falcon.restaurants.domain.interactor.FetchRestaurantsUseCase
import com.falcon.restaurants.domain.repository.MealRepository
import com.falcon.restaurants.domain.repository.RestaurantRepository
import com.falcon.restaurants.presentation.mapper.MealMapper
import com.falcon.restaurants.presentation.screens.meal.MealViewModel
import com.falcon.restaurants.presentation.screens.meal.MealViewModelFactory
import com.falcon.restaurants.presentation.screens.restaurant.RestaurantViewModel
import com.falcon.restaurants.presentation.screens.restaurant.RestaurantViewModelFactory
import com.falcon.restaurants.presentation.screens.splash.SplashViewModel
import com.falcon.restaurants.presentation.screens.splash.SplashViewModelFactory
import dagger.Module
import dagger.Provides


@Module
class PresentationModule {

    // daos
    @Provides
    fun getMealDao(roomDB: RoomDB): MealModelDao = roomDB.mealDao()

    @Provides
    fun getRestaurantDao(roomDB: RoomDB): RestaurantModelDao = roomDB.restaurantDao()

    // repositories
    @Provides
    fun getRestaurantRepository(
        restaurantModelDao: RestaurantModelDao,
        fetchRestaurantsEndPoint: FetchRestaurantsEndPoint,
        restaurantModelMapper: RestaurantModelMapper
    ): RestaurantRepository {
        return RestaurantRepositoryImpl(restaurantModelDao, fetchRestaurantsEndPoint, restaurantModelMapper)
    }

    @Provides
    fun getMealRepository(
        mealModelDao: MealModelDao,
        fetchMealsEndPoint: FetchMealsEndPoint,
        mealModelMapper: MealModelMapper
    ): MealRepository {
        return MealRepositoryImpl(mealModelDao, fetchMealsEndPoint, mealModelMapper)
    }

    // view models
    @Provides
    fun getRestaurantViewModel(fragmentActivity: AppCompatActivity,
                               application: Application,
                               fetchRestaurantsUseCase: FetchRestaurantsUseCase,
                               restaurantMapper: RestaurantMapper
                               ): RestaurantViewModel{

        return ViewModelProviders.of(
                fragmentActivity,
                RestaurantViewModelFactory(application, fetchRestaurantsUseCase, restaurantMapper)
        ).get(RestaurantViewModel::class.java)
    }

    @Provides
    fun getMealViewModel(fragmentActivity: AppCompatActivity,
                         application: Application,
                         fetchMealsUseCase: FetchMealsUseCase,
                         mealMapper: MealMapper
                         ): MealViewModel{

        return ViewModelProviders.of(
                fragmentActivity,
                MealViewModelFactory(application, fetchMealsUseCase, mealMapper)
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
