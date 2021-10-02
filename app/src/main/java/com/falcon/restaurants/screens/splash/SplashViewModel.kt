package com.falcon.restaurants.screens.splash

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.falcon.restaurants.screens.restaurant.RestaurantViewModel
import com.falcon.restaurants.screens.meal.MealViewModel
import com.falcon.restaurants.utils.Logger

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class SplashViewModel (val application: Application,
                       val restaurantViewModel: RestaurantViewModel,
                       val mealViewModel: MealViewModel
) : ViewModel() {

    val TAG: String = "SplashViewModel"


    interface AllUpsertedListener{
        fun onSuccess()
        fun onFailed(e: Throwable)
    }

    @SuppressLint("CheckResult")
    fun fetchAllData(allUpsertedListener: AllUpsertedListener){

        val observer: Observer<String> = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Logger.log( TAG, "onSubscribe: ")
            }

            override fun onNext(string: String) {
                Logger.log( TAG, "onNext: " + string)
            }

            override fun onError(e: Throwable) {
                Logger.log( TAG, "onError: " + e.getLocalizedMessage())
                allUpsertedListener.onFailed(e)
            }

            override fun onComplete() {
                Logger.log(TAG,  "onComplete: ")
                allUpsertedListener.onSuccess()
            }

        }

        Logger.log( TAG, "startDownload: ")

        Observable.zip(
                mealViewModel.fetch(),
                restaurantViewModel.fetch(),
                object : BiFunction<String, String, String>{
                    @io.reactivex.annotations.NonNull
                    override fun apply(@io.reactivex.annotations.NonNull s: String,
                                        @io.reactivex.annotations.NonNull s2: String): String {
                        Logger.log( TAG, "apply: s: " + s + " , s2: " + s2)
                        return s + " " + s2
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()).subscribe(observer)
    }

}
