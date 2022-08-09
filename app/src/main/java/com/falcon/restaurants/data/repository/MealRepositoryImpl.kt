package com.falcon.restaurants.data.repository
import android.annotation.SuppressLint
import com.falcon.restaurants.data.mapper.MealModelMapper
import com.falcon.restaurants.data.network.RetrofitInterface
import com.falcon.restaurants.data.network.meal.MealNet
import com.falcon.restaurants.data.room.meal.MealModel
import com.falcon.restaurants.data.room.meal.MealModelDao
import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.repository.MealRepository
import com.falcon.restaurants.domain.utils.Logger
import io.reactivex.Completable
import javax.inject.Inject
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MealRepositoryImpl @Inject constructor (
    val mealModelDao: MealModelDao,
    val retrofitInterface: RetrofitInterface,
    val mealModelMapper: MealModelMapper

    ) : MealRepository {

    val TAG: String = "MealRepositoryImpl"

    @SuppressLint("CheckResult")
    override fun fetchAndUpsert(): Completable {

        return Completable.defer {
            val maxUpdatedAt: String = mealModelDao.getMaxUpdated()
            // this is for test
            //val maxUpdatedAt: String = "1970-01-01 00:00:01"
            retrofitInterface.getMeals(maxUpdatedAt)
                .flatMapCompletable { mealNets -> upsert(mealNets) }
        }.subscribeOn(Schedulers.io())

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

    override fun upsert(mealNets: List<MealNet>) : Completable{
        return  Completable.fromAction {
            for (mealCurrent in mealNets) {
                upsert(mealCurrent)
            }
        }
    }

}
