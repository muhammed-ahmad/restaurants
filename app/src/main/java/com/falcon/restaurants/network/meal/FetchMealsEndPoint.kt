package com.falcon.restaurants.network.meal

import com.falcon.restaurants.network.RetrofitInterface
import com.falcon.restaurants.utils.Logger
import javax.inject.Inject
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FetchMealsEndPoint @Inject constructor(val retrofitInterface: RetrofitInterface){

    val TAG = "FetchMealsEndPoint"

    interface listener{
        fun onFetchSuccess(mealNets: List<MealNet>)
        fun onFetchFailed(e: Throwable)
    }

    fun fetch(maxUpdatedAt: String , listener: listener) {

        val single: Single<List<MealNet>> = retrofitInterface.getMeals(maxUpdatedAt)

        val singleObserver: SingleObserver<List<MealNet>>  = object : SingleObserver<List<MealNet>> {
            
            override fun onSubscribe(d: Disposable) {
                Logger.log( TAG, "onSubscribe: ")
            }
            
            override fun onSuccess(mealNets: List<MealNet>) {
                Logger.log( TAG, "onSuccess: ")
                listener.onFetchSuccess(mealNets)
            }

            override fun onError(e: Throwable) {
                Logger.log( TAG, "onError: e: " + e.getLocalizedMessage())
                listener.onFetchFailed(e)
            }
        }

        single.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(singleObserver)
    }

}
