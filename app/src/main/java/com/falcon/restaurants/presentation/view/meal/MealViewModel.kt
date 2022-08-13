package com.falcon.restaurants.presentation.view.meal
import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.falcon.restaurants.domain.interactor.meal.FetchAndUpsertMealUseCase
import com.falcon.restaurants.domain.interactor.meal.GetMealByIdUseCase
import com.falcon.restaurants.domain.interactor.meal.GetMealsByRestaurantIdUseCase
import com.falcon.restaurants.domain.util.Logger
import com.falcon.restaurants.presentation.mapper.MealMapper
import com.falcon.restaurants.presentation.model.MealUiModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MealViewModel (
    val application: Application,
    val fetchAndUpsertMealUseCase: FetchAndUpsertMealUseCase,
    val getMealsByRestaurantIdUseCase: GetMealsByRestaurantIdUseCase,
    val getMealByIdUseCase: GetMealByIdUseCase,
    val mealMapper: MealMapper

    ): ViewModel() {

    val TAG: String = "MealViewModel"
    lateinit var mealsMutableLiveData: MutableLiveData<List<MealUiModel>>

    interface GetMealByIdListener{
        fun onSuccess(mealModel: MealUiModel)
        fun onFailed(throwable: Throwable)
    }

    fun fetchAndUpsert() : Completable = fetchAndUpsertMealUseCase.execute()

    @SuppressLint("CheckResult")
    fun getByRestaurantId(restaurantId: String) : LiveData<List<MealUiModel>> {

        if(!::mealsMutableLiveData.isInitialized) {
               mealsMutableLiveData = MutableLiveData<List<MealUiModel>>()
               getMealsByRestaurantIdUseCase.execute(restaurantId)
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
   fun getMealById(mealId: String, getMealByIdListener: GetMealByIdListener) {
       getMealByIdUseCase.execute(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { meal -> getMealByIdListener.onSuccess(mealMapper.toPresentation(meal)) },
                    { throwable -> getMealByIdListener.onFailed(throwable) }
                )
    }

}
