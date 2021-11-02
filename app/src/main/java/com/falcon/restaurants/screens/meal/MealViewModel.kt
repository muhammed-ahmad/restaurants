package com.falcon.restaurants.screens.meal
import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.falcon.restaurants.room.meal.Meal
import com.falcon.restaurants.utils.Logger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MealViewModel (
        val application: Application,
        val fetchMealsUseCase: FetchMealsUseCase
    ): ViewModel() {

    val TAG: String = "MealViewModel"
    lateinit var mealsMutableLiveData: MutableLiveData<List<Meal>>

    interface ListenerForOne{
        fun onQuerySuccess(meal: Meal)
        fun onQueryFailed(throwable: Throwable)
    }

    fun fetch() : Observable<String> = fetchMealsUseCase.fetch()

    @SuppressLint("CheckResult")
    fun getByRestaurantId(restaurantId: String) : LiveData<List<Meal>> {

        if(!::mealsMutableLiveData.isInitialized) {
               mealsMutableLiveData = MutableLiveData<List<Meal>>()
               fetchMealsUseCase.getByRestaurantId(restaurantId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe( { meals -> Logger.log( TAG, "onNext: " + meals.size)
                                           mealsMutableLiveData.setValue(meals) },
                                { throwable -> Logger.log(TAG,  "onError: " + throwable.localizedMessage) },
                                { Logger.log( TAG, "onComplete: ") },
                                { disposable -> Logger.log( TAG, "onSubscribe: ") } )
        }
        return mealsMutableLiveData
    }

   @SuppressLint("CheckResult")
   fun getMealById(mealId: String, listener: ListenerForOne) {
       fetchMealsUseCase.getMealById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { meal -> listener.onQuerySuccess(meal) },
                    { throwable -> listener.onQueryFailed(throwable) }
                )
    }

}
