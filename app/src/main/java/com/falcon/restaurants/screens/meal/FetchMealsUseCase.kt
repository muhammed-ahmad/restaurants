package com.falcon.restaurants.screens.meal
import android.annotation.SuppressLint
import com.falcon.restaurants.network.meal.FetchMealsEndPoint
import com.falcon.restaurants.network.meal.MealNet
import com.falcon.restaurants.room.meal.Meal
import com.falcon.restaurants.room.meal.MealDao
import com.falcon.restaurants.utils.Logger
import javax.inject.Inject
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single

class FetchMealsUseCase @Inject constructor (
    val mealDao: MealDao,
    val fetchMealsEndPoint: FetchMealsEndPoint
    ) {

    val TAG: String = "FetchMealsUseCase"

    @SuppressLint("CheckResult")
    fun fetch(): Observable<String>{
        return Observable.create{emitter ->
                val  maxUpdatedAt: String = mealDao.getMaxUpdated()

                fetchMealsEndPoint.fetch(maxUpdatedAt, object: FetchMealsEndPoint.Listener{

                    override fun onFetchSuccess(mealNets: List<MealNet>) {
                        Logger.log( TAG, "onFetchSuccess: " + mealNets.size)
                        upsert(mealNets)
                        emitter.onNext("meal completed")
                        emitter.onComplete()
                    }

                    override fun onFetchFailed(e: Throwable) {
                        Logger.log( TAG, "onFetchFailed: e: " + e.getLocalizedMessage())
                        emitter.onError(e)
                    }
                })

        }
    }

    fun getByRestaurantId(typeIdV: String): Observable<List<Meal>> {
        return mealDao.getByRestaurantId(typeIdV)
    }

    fun getMealById(mealId: String): Single<Meal> {
        return mealDao.getMealById(mealId)
    }

    fun upsert(mealNet: MealNet) {
            mealDao.upsert(Meal(
                    mealNet.id,
                    mealNet.name,
                    mealNet.details,
                    mealNet.imageUrl,
                    mealNet.restaurantId,
                    mealNet.updatedAt,
                    mealNet.active,
                    mealNet.favorite
            ))
    }

    fun upsert(mealNets: List<MealNet>) {
        for (mealCurrent in mealNets) {
            upsert(mealCurrent)
        }
    }
}
