package com.falcon.restaurants.data.repository
import android.annotation.SuppressLint
import com.falcon.restaurants.data.mapper.MealModelMapper
import com.falcon.restaurants.data.network.meal.FetchMealsEndPoint
import com.falcon.restaurants.data.network.meal.MealNet
import com.falcon.restaurants.data.room.meal.MealModel
import com.falcon.restaurants.data.room.meal.MealModelDao
import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.repository.MealRepository
import com.falcon.restaurants.domain.utils.Logger
import com.falcon.restaurants.presentation.mapper.MealMapper
import javax.inject.Inject
import io.reactivex.Observable
import io.reactivex.Single

class MealRepositoryImpl @Inject constructor (
    val mealModelDao: MealModelDao,
    val fetchMealsEndPoint: FetchMealsEndPoint,
    val mealModelMapper: MealModelMapper
    ) : MealRepository {

    val TAG: String = "MealRepositoryImpl"

    @SuppressLint("CheckResult")
    override fun fetch(): Observable<String>{
        return Observable.create{emitter ->
                val  maxUpdatedAt: String = mealModelDao.getMaxUpdated()

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

    override fun getByRestaurantId(typeIdV: String): Observable<List<Meal>> {
        return mealModelDao.getByRestaurantId(typeIdV).map {
             mealModels -> mealModelMapper.toDomainList(mealModels)
         }
    }

    override fun getMealById(mealId: String): Single<Meal> {
        return mealModelDao.getMealById(mealId).map {
            mealModel -> mealModelMapper.toDomain(mealModel)
        }
    }

    override fun upsert(mealNet: MealNet) {
            mealModelDao.upsert(MealModel(
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

    override fun upsert(mealNets: List<MealNet>) {
        for (mealCurrent in mealNets) {
            upsert(mealCurrent)
        }
    }

}
