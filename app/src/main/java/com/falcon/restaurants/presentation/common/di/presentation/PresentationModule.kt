package com.falcon.restaurants.presentation.common.di.presentation
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.falcon.models.presentation.mapper.RestaurantMapper
import com.falcon.restaurants.data.mapper.MealDataMapper
import com.falcon.restaurants.data.mapper.RestaurantDataMapper
import com.falcon.restaurants.data.network.RetrofitInterface
import com.falcon.restaurants.data.repository.MealRepositoryImpl
import com.falcon.restaurants.data.repository.RestaurantRepositoryImpl
import com.falcon.restaurants.data.room.RoomDB
import com.falcon.restaurants.data.room.meal.MealDataDao
import com.falcon.restaurants.data.room.restaurant.RestaurantDataDao
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
    fun getMealDao(roomDB: RoomDB): MealDataDao = roomDB.mealDao()

    @Provides
    fun getRestaurantDao(roomDB: RoomDB): RestaurantDataDao = roomDB.restaurantDao()

    // repositories
    @Provides
    fun getRestaurantRepository(
        restaurantDataDao: RestaurantDataDao,
        retrofitInterface: RetrofitInterface,
        restaurantDataMapper: RestaurantDataMapper
    ): RestaurantRepository {
        return RestaurantRepositoryImpl(restaurantDataDao, retrofitInterface, restaurantDataMapper)
    }

    @Provides
    fun getMealRepository(
        mealDataDao: MealDataDao,
        retrofitInterface: RetrofitInterface,
        mealDataMapper: MealDataMapper
    ): MealRepository {
        return MealRepositoryImpl(mealDataDao, retrofitInterface, mealDataMapper)
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
