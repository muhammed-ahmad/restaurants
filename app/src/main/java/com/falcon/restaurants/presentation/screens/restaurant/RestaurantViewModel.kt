package com.falcon.restaurants.presentation.screens.restaurant

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.falcon.models.presentation.mapper.RestaurantMapper
import com.falcon.restaurants.domain.interactor.LocalListeners
import com.falcon.restaurants.domain.interactor.FetchRestaurantsUseCase
import com.falcon.restaurants.domain.utils.Logger
import com.falcon.restaurants.presentation.model.RestaurantUiModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RestaurantViewModel(
        val application: Application,
        val fetchRestaurantsUseCase: FetchRestaurantsUseCase,
        var restaurantMapper: RestaurantMapper
    ) : ViewModel() {

    val TAG: String = "RestaurantViewModel"
    lateinit var restaurantsMutableLiveData: MutableLiveData<List<RestaurantUiModel>>

    fun fetch(): Observable<String> = fetchRestaurantsUseCase.fetch()

    @SuppressLint("CheckResult")
    fun getByParentId(restaurantId: String): LiveData<List<RestaurantUiModel>> {

        if (!::restaurantsMutableLiveData.isInitialized) {
            restaurantsMutableLiveData = MutableLiveData<List<RestaurantUiModel>>()

            fetchRestaurantsUseCase.getByParentId(restaurantId)
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

    @SuppressLint("CheckResult")
    fun hasChildren(id: String, listener: LocalListeners.OnSuccessListener) {

        fetchRestaurantsUseCase.hasChildren(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { hasChildren -> listener.onSuccess(hasChildren) },
                            { throwable -> listener.onFailed() })

    }

}
