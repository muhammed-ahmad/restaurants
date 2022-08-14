package com.falcon.restaurants.presentation.view.restaurant

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.falcon.models.presentation.mapper.RestaurantMapper
import com.falcon.restaurants.domain.interactor.restaurant.FetchAndUpsertRestaurantsUseCase
import com.falcon.restaurants.domain.interactor.restaurant.GetRestaurantsUseCase
import com.falcon.restaurants.domain.util.Logger
import com.falcon.restaurants.presentation.model.RestaurantUiModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RestaurantViewModel(
    val application: Application,
    val fetchAndUpsertRestaurantsUseCase: FetchAndUpsertRestaurantsUseCase,
    val getRestaurantsUseCase: GetRestaurantsUseCase,
    var restaurantMapper: RestaurantMapper

    ) : ViewModel() {

    val TAG: String = "RestaurantViewModel"
    lateinit var restaurantsMutableLiveData: MutableLiveData<List<RestaurantUiModel>>


    fun fetchAndUpsert(): Completable = fetchAndUpsertRestaurantsUseCase.execute().subscribeOn(Schedulers.io())

    @SuppressLint("CheckResult")
    fun getRestaurants(): LiveData<List<RestaurantUiModel>> {

        if (!::restaurantsMutableLiveData.isInitialized) {
            restaurantsMutableLiveData = MutableLiveData<List<RestaurantUiModel>>()

            getRestaurantsUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { restaurants -> Logger.log( TAG, "onNext: ")
                              val restaurantUiModels = restaurantMapper.toPresentationList(restaurants)
                              restaurantsMutableLiveData.setValue(restaurantUiModels) },
                            { throwable -> Logger.log( TAG, "onError: " + throwable.localizedMessage) },
                            { Logger.log( TAG, "onComplete: ") },
                            { disposable ->  Logger.log( TAG, "onSubscribe: ") } )

        }
        return restaurantsMutableLiveData
    }

}
