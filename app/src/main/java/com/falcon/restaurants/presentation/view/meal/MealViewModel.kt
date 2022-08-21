package com.falcon.restaurants.presentation.view.meal
import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.falcon.restaurants.domain.interactor.meal.FetchAndUpsertMealUseCase
import com.falcon.restaurants.domain.interactor.meal.GetMealByIdUseCase
import com.falcon.restaurants.domain.interactor.meal.GetMealsByRestaurantIdUseCase
import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.util.Logger
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MealViewModel (
    val application: Application,
    val fetchAndUpsertMealUseCase: FetchAndUpsertMealUseCase,
    val getMealsByRestaurantIdUseCase: GetMealsByRestaurantIdUseCase,
    val getMealByIdUseCase: GetMealByIdUseCase,

    ): ViewModel() {

    val TAG: String = "MealViewModel"

    interface GetMealByIdListener{
        fun onSuccess(meal: Meal)
        fun onFailed(throwable: Throwable)
    }

    fun fetchAndUpsert() : Completable = fetchAndUpsertMealUseCase.execute().subscribeOn(Schedulers.io())

    lateinit var mealsMutableLiveData: MutableLiveData<List<Meal>>
    @SuppressLint("CheckResult")
    fun getMealsByRestaurantId(restaurantId: String) : LiveData<List<Meal>> {

        if(!::mealsMutableLiveData.isInitialized) {
               mealsMutableLiveData = MutableLiveData<List<Meal>>()
               getMealsByRestaurantIdUseCase.execute(restaurantId)
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
   fun getMealById(mealId: String, getMealByIdListener: GetMealByIdListener) {
       getMealByIdUseCase.execute(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { meal -> getMealByIdListener.onSuccess(meal) },
                    { throwable -> getMealByIdListener.onFailed(throwable) }
                )
   }

}
