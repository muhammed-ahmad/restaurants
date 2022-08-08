package com.falcon.restaurants.data.network.meal
import android.annotation.SuppressLint
import com.falcon.restaurants.data.network.RetrofitInterface
import com.falcon.restaurants.domain.utils.Logger
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FetchMealsEndPoint @Inject constructor(val retrofitInterface: RetrofitInterface){

    val TAG = "FetchMealsEndPoint"

    interface Listener{
        fun onFetchSuccess(mealNets: List<MealNet>)
        fun onFetchFailed(e: Throwable)
    }

    @SuppressLint("CheckResult")
    fun fetch(maxUpdatedAt: String, Listener: Listener) {

        val single: Single<List<MealNet>> = retrofitInterface.getMeals(maxUpdatedAt)

        single.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe (
            { mealNets -> Logger.log( TAG, "onSuccess: ")
                          Listener.onFetchSuccess(mealNets)
            },
            { throwable -> Logger.log( TAG, "onError: e: " + throwable.getLocalizedMessage())
                           Listener.onFetchFailed(throwable)
            }
        )

    }

}
