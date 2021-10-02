package com.falcon.restaurants.screens.meal

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.falcon.restaurants.room.meal.Meal
import com.falcon.restaurants.utils.Logger
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MealViewModel (
        val application: Application,
        val fetchMealsUseCase: FetchMealsUseCase
    ): ViewModel() {

    val TAG: String = "MealViewModel"
    lateinit var mealsMutableLiveData: MutableLiveData<List<Meal>>

    interface Listener{
        fun onQuerySuccess(meals: List<Meal>)
        fun onQueryFailed(e: Throwable)
    }

    interface ListenerForOne{
        fun onQuerySuccess(meal: Meal)
        fun onQueryFailed(e: Throwable)
    }

    fun fetch() : Observable<String>{
        return fetchMealsUseCase.fetch()
    }

    @SuppressLint("CheckResult")
    fun getByRestaurantId(restaurantId: String) : LiveData<List<Meal>> {
        if(!::mealsMutableLiveData.isInitialized) {
               mealsMutableLiveData = MutableLiveData<List<Meal>>()
               fetchMealsUseCase.getByRestaurantId(restaurantId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            object : Observer<List<Meal>>{
                                override fun onSubscribe(d: Disposable) {
                                    Logger.log( TAG, "onSubscribe: ")
                                }

                                override fun onNext(meals: List<Meal>) {
                                    Logger.log( TAG, "onNext: " + meals.size)
                                    mealsMutableLiveData.setValue(meals)
                                }

                                override fun onError(e: Throwable) {
                                    Logger.log(TAG,  "onError: " + e.getLocalizedMessage())
                                }

                                override fun onComplete() {
                                    Logger.log( TAG, "onComplete: ")
                                }

                            })
        }
        return mealsMutableLiveData
    }

   fun getMealById(mealId: String, listener: ListenerForOne) {

       val singleObserver: SingleObserver<Meal> = object: SingleObserver<Meal> {

            override fun onSubscribe(d: Disposable) {}

            override fun onSuccess(meal: Meal) {
                listener.onQuerySuccess(meal)
            }

            override fun onError(e: Throwable) {
                listener.onQueryFailed(e)
            }
        }

        fetchMealsUseCase.getMealById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleObserver)
    }

}
