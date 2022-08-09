package com.falcon.restaurants.presentation.screens.meal
import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.falcon.restaurants.domain.interactor.FetchMealsUseCase
import com.falcon.restaurants.domain.utils.Logger
import com.falcon.restaurants.presentation.mapper.MealMapper
import com.falcon.restaurants.presentation.model.MealUiModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MealViewModel (
        val application: Application,
        val fetchMealsUseCase: FetchMealsUseCase,
        val mealMapper: MealMapper
    ): ViewModel() {

    val TAG: String = "MealViewModel"
    lateinit var mealsMutableLiveData: MutableLiveData<List<MealUiModel>>

    interface ListenerForOne{
        fun onQuerySuccess(mealModel: MealUiModel)
        fun onQueryFailed(throwable: Throwable)
    }

    fun fetchAndUpsert() : Completable = fetchMealsUseCase.fetchAndUpsert()

    @SuppressLint("CheckResult")
    fun getByRestaurantId(restaurantId: String) : LiveData<List<MealUiModel>> {

        if(!::mealsMutableLiveData.isInitialized) {
               mealsMutableLiveData = MutableLiveData<List<MealUiModel>>()
               fetchMealsUseCase.getByRestaurantId(restaurantId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe( { meals -> Logger.log( TAG, "onNext: " + meals.size)
                                           val mealUiModels = mealMapper.toPresentationList(meals)
                                           mealsMutableLiveData.setValue(mealUiModels) },
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
                    { meal ->
                        listener.onQuerySuccess(mealMapper.toPresentation(meal)) },
                    { throwable -> listener.onQueryFailed(throwable) }
                )
    }

}
