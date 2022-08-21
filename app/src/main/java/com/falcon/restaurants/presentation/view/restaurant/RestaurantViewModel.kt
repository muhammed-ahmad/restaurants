package com.falcon.restaurants.presentation.view.restaurant

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.falcon.restaurants.domain.interactor.restaurant.FetchAndUpsertRestaurantsUseCase
import com.falcon.restaurants.domain.interactor.restaurant.GetRestaurantsUseCase
import com.falcon.restaurants.domain.model.Restaurant
import com.falcon.restaurants.domain.util.Logger
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RestaurantViewModel(
    val application: Application,
    val fetchAndUpsertRestaurantsUseCase: FetchAndUpsertRestaurantsUseCase,
    val getRestaurantsUseCase: GetRestaurantsUseCase,

    ) : ViewModel() {

    val TAG: String = "RestaurantViewModel"


    fun fetchAndUpsert(): Completable = fetchAndUpsertRestaurantsUseCase.execute().subscribeOn(Schedulers.io())

    lateinit var restaurantsMutableLiveData: MutableLiveData<List<Restaurant>>
    @SuppressLint("CheckResult")
    fun getRestaurants(): LiveData<List<Restaurant>> {

        if (!::restaurantsMutableLiveData.isInitialized) {
            restaurantsMutableLiveData = MutableLiveData<List<Restaurant>>()

            getRestaurantsUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { restaurants -> Logger.log( TAG, "onNext: ")
                              restaurantsMutableLiveData.setValue(restaurants) },
                            { throwable -> Logger.log( TAG, "onError: " + throwable.localizedMessage) },
                            { Logger.log( TAG, "onComplete: ") },
                            { disposable ->  Logger.log( TAG, "onSubscribe: ") } )

        }
        return restaurantsMutableLiveData
    }

}
