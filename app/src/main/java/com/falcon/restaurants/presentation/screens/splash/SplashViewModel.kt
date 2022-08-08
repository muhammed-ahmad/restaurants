package com.falcon.restaurants.presentation.screens.splash

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.ViewModel
import com.falcon.restaurants.presentation.screens.meal.MealViewModel
import com.falcon.restaurants.presentation.screens.restaurant.RestaurantViewModel
import com.falcon.restaurants.domain.utils.Logger
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class SplashViewModel (
        val application: Application,
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

        Logger.log( TAG, "startDownload: ")

        Observable
                .zip(
                    mealViewModel.fetch(),
                    restaurantViewModel.fetch(),
                    { s, s2 -> Logger.log( TAG, "apply: s: $s , s2: $s2")
                               "$s $s2"
                    })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe( { string -> Logger.log( TAG, "onNext: $string") },
                            { throwable -> Logger.log( TAG, "onError: " + throwable.localizedMessage)
                                           allUpsertedListener.onFailed(throwable) },
                            { Logger.log(TAG,  "onComplete: ")
                              allUpsertedListener.onSuccess() },
                            { disposable -> Logger.log( TAG, "onSubscribe: ") })
    }

}
