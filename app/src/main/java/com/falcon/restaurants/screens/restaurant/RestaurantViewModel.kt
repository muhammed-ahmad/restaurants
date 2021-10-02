package com.falcon.restaurants.screens.restaurant

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.falcon.restaurants.base.LocalListeners
import com.falcon.restaurants.room.restaurant.Restaurant
import com.falcon.restaurants.utils.Logger
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RestaurantViewModel(@androidx.annotation.NonNull val application: Application,
                          val fetchRestaurantsUseCase: FetchRestaurantsUseCase) : ViewModel() {

    val TAG: String = "RestaurantViewModel"

    lateinit var restaurantsMutableLiveData: MutableLiveData<List<Restaurant>>

    fun fetch(): Observable<String> {
       return fetchRestaurantsUseCase.fetch()
    }

    fun getByParentId(parentId: String): LiveData<List<Restaurant>> {

        if (!::restaurantsMutableLiveData.isInitialized) {
            restaurantsMutableLiveData = MutableLiveData<List<Restaurant>>()

            fetchRestaurantsUseCase.getByParentId(parentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( object: Observer<List<Restaurant>>{
                                    override fun onSubscribe(d: Disposable) {
                                        Logger.log( TAG, "onSubscribe: ")

                                    }

                                    override fun onNext(categories: List<Restaurant>) {
                                        Logger.log( TAG, "onNext: ")
                                        restaurantsMutableLiveData.setValue(categories)
                                    }

                                    override fun onError(e: Throwable) {
                                        Logger.log( TAG, "onError: ")
                                    }

                                    override fun onComplete() {
                                        Logger.log( TAG, "onComplete: ")
                                    }

                                }
                )
        }

        return restaurantsMutableLiveData
    }

    fun hasChildren(id: String, listener: LocalListeners.OnSuccessListener) {

        fetchRestaurantsUseCase
                .hasChildren(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Boolean> {
                            override fun onSubscribe(@NonNull d: Disposable) {
                            }

                            override fun onSuccess(@NonNull hasChildren: Boolean) {
                                listener.onSuccess(hasChildren)
                            }

                            override fun onError(@NonNull e: Throwable) {
                                listener.onFailed()
                            }
                        }
                )
    }

}
