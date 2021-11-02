package com.falcon.restaurants.screens.restaurant

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.falcon.restaurants.base.LocalListeners
import com.falcon.restaurants.room.restaurant.Restaurant
import com.falcon.restaurants.utils.Logger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RestaurantViewModel(
        val application: Application,
        val fetchRestaurantsUseCase: FetchRestaurantsUseCase
    ) : ViewModel() {

    val TAG: String = "RestaurantViewModel"
    lateinit var restaurantsMutableLiveData: MutableLiveData<List<Restaurant>>

    fun fetch(): Observable<String> = fetchRestaurantsUseCase.fetch()

    @SuppressLint("CheckResult")
    fun getByParentId(restaurantId: String): LiveData<List<Restaurant>> {

        if (!::restaurantsMutableLiveData.isInitialized) {
            restaurantsMutableLiveData = MutableLiveData<List<Restaurant>>()

            fetchRestaurantsUseCase.getByParentId(restaurantId)
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

    @SuppressLint("CheckResult")
    fun hasChildren(id: String, listener: LocalListeners.OnSuccessListener) {

        fetchRestaurantsUseCase.hasChildren(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { hasChildren -> listener.onSuccess(hasChildren) },
                            { throwable -> listener.onFailed() })

    }

}
