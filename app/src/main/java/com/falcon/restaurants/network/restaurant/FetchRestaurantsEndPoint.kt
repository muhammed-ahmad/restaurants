package com.falcon.restaurants.network.restaurant
import android.annotation.SuppressLint
import com.falcon.restaurants.network.RetrofitInterface
import com.falcon.restaurants.utils.Logger
import javax.inject.Inject
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

open class FetchRestaurantsEndPoint @Inject constructor(val retrofitInterface: RetrofitInterface){

    val TAG: String = "FetchRestaurantEndPoint"

    interface Listener {
        fun onFetchSuccess(restaurantNets: List<RestaurantNet>)
        fun onFetchFailed(e: Throwable)
    }

    @SuppressLint("CheckResult")
    open fun fetch(maxUpdatedAt: String, listener: Listener) {

        val single: Single<List<RestaurantNet>> = retrofitInterface.getRestaurants(maxUpdatedAt)

        single.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                    { restaurantNets -> Logger.log(TAG , "onSuccess: ")
                        listener.onFetchSuccess(restaurantNets)},
                    { throwable -> Logger.log(TAG , "onError: " + throwable.getLocalizedMessage())
                        listener.onFetchFailed(throwable)}
                )
    }

}
