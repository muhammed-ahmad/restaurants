package com.falcon.restaurants.presentation.common.di.presentation
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.falcon.restaurants.data.mapper.MealDataMapper
import com.falcon.restaurants.data.mapper.RestaurantDataMapper
import com.falcon.restaurants.data.net.RetrofitInterface
import com.falcon.restaurants.data.repository.MealRepositoryImpl
import com.falcon.restaurants.data.repository.RestaurantRepositoryImpl
import com.falcon.restaurants.data.db.RoomDB
import com.falcon.restaurants.data.db.dao.MealDataDao
import com.falcon.restaurants.data.db.dao.RestaurantDataDao
import com.falcon.restaurants.domain.interactor.meal.FetchAndUpsertMealUseCase
import com.falcon.restaurants.domain.interactor.meal.GetMealByIdUseCase
import com.falcon.restaurants.domain.interactor.meal.GetMealsByRestaurantIdUseCase
import com.falcon.restaurants.domain.interactor.restaurant.FetchAndUpsertRestaurantsUseCase
import com.falcon.restaurants.domain.interactor.restaurant.GetRestaurantsUseCase
import com.falcon.restaurants.domain.repository.MealRepository
import com.falcon.restaurants.domain.repository.RestaurantRepository
import com.falcon.restaurants.presentation.view.meal.MealViewModel
import com.falcon.restaurants.presentation.view.meal.MealViewModelFactory
import com.falcon.restaurants.presentation.view.restaurant.RestaurantViewModel
import com.falcon.restaurants.presentation.view.restaurant.RestaurantViewModelFactory
import com.falcon.restaurants.presentation.view.splash.SplashViewModel
import com.falcon.restaurants.presentation.view.splash.SplashViewModelFactory
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
                               fetchAndUpsertRestaurantsUseCase: FetchAndUpsertRestaurantsUseCase,
                               getRestaurantsUseCase: GetRestaurantsUseCase
                               ): RestaurantViewModel{

        return ViewModelProviders.of(
                fragmentActivity,
                RestaurantViewModelFactory(
                    application,
                    fetchAndUpsertRestaurantsUseCase,
                    getRestaurantsUseCase
                    )
        ).get(RestaurantViewModel::class.java)
    }

    @Provides
    fun getMealViewModel(fragmentActivity: AppCompatActivity,
                         application: Application,
                         fetchAndUpsertMealUseCase: FetchAndUpsertMealUseCase,
                         getMealsByRestaurantIdUseCase: GetMealsByRestaurantIdUseCase,
                         getMealByIdUseCase: GetMealByIdUseCase
                         ): MealViewModel{

        return ViewModelProviders.of(
                fragmentActivity,
                MealViewModelFactory(
                    application,
                    fetchAndUpsertMealUseCase,
                    getMealsByRestaurantIdUseCase,
                    getMealByIdUseCase
                    )
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
