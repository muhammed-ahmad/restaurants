package com.falcon.restaurants.presentation.screens.splash

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.ViewModel
import com.falcon.restaurants.domain.utils.Logger
import com.falcon.restaurants.presentation.screens.meal.MealViewModel
import com.falcon.restaurants.presentation.screens.restaurant.RestaurantViewModel
import io.reactivex.Completable
import io.reactivex.CompletableObserver
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

        val mealCompletable: Completable = mealViewModel.fetchAndUpsert()
        val restaurantCompletable: Completable = restaurantViewModel.fetchAndUpsert()
        val all = Completable.mergeArray(mealCompletable, restaurantCompletable)

        all.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(
                { Logger.log(TAG,  "onComplete: ")
                    allUpsertedListener.onSuccess() }
            ) { throwable -> Logger.log( TAG, "onError: " + throwable.localizedMessage)
                allUpsertedListener.onFailed(throwable) }

        Completable.complete()


    }

}
