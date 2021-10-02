package com.falcon.restaurants.network.restaurant

import com.falcon.restaurants.network.RetrofitInterface
import com.falcon.restaurants.utils.Logger
import javax.inject.Inject
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FetchRestaurantsEndPoint @Inject constructor(val retrofitInterface: RetrofitInterface){

    val TAG: String = "FetchRestaurantEndPoint"

    interface Listener {
        fun onFetchSuccess(restaurantNets: List<RestaurantNet>)
        fun onFetchFailed(e: Throwable)
    }

    fun fetch(maxUpdatedAt: String, listener: Listener) {

        val single: Single<List<RestaurantNet>> = retrofitInterface.getRestaurants(maxUpdatedAt)
        var singleObserver: SingleObserver<List<RestaurantNet>> = object : SingleObserver<List<RestaurantNet>> {
            
            override fun onSubscribe(d: Disposable) {
                Logger.log(TAG , "onSubscribe: ")
            }

            override fun onSuccess(restaurantNets: List<RestaurantNet>) {
                Logger.log(TAG , "onSuccess: ")
                listener.onFetchSuccess(restaurantNets)
            }

            override fun onError(e: Throwable) {
                Logger.log(TAG , "onError: " + e.getLocalizedMessage())
                listener.onFetchFailed(e)
            }
        }

        single.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(singleObserver)
    }
}
