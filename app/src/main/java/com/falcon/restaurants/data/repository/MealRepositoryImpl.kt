package com.falcon.restaurants.data.repository
import android.annotation.SuppressLint
import com.falcon.restaurants.data.mapper.MealDataMapper
import com.falcon.restaurants.data.network.RetrofitInterface
import com.falcon.restaurants.data.room.meal.MealDataDao
import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.repository.MealRepository
import io.reactivex.Completable
import javax.inject.Inject
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MealRepositoryImpl @Inject constructor (
    val mealDataDao: MealDataDao,
    val retrofitInterface: RetrofitInterface,
    val mealDataMapper: MealDataMapper

    ) : MealRepository {

    val TAG: String = "MealRepositoryImpl"

    @SuppressLint("CheckResult")
    override fun fetchAndUpsert(): Completable {

        return Completable.defer {
            val maxUpdatedAt: String = mealDataDao.getMaxUpdated()
            // this is for test
            //val maxUpdatedAt: String = "1970-01-01 00:00:01"
            retrofitInterface.getMealDtos(maxUpdatedAt)
                             .flatMapCompletable {
                                     mealDtos -> upsert(mealDataMapper.dtoToDomainList(mealDtos))
                             }
        }.subscribeOn(Schedulers.io())

    }

    override fun getMealsByRestaurantId(typeIdV: String): Observable<List<Meal>> {
        return mealDataDao.getMealsByRestaurantId(typeIdV).map {
             mealModels -> mealDataMapper.dataToDomainList(mealModels)
         }
    }

    override fun getMealById(mealId: String): Single<Meal> {
        return mealDataDao.getMealById(mealId).map {
            mealModel -> mealDataMapper.dataToDomain(mealModel)
        }
    }

    override fun upsert(meal: Meal) {
            mealDataDao.upsert(mealDataMapper.domainToData(meal))
    }

    override fun upsert(meals: List<Meal>) : Completable{
        return  Completable.fromAction { meals.map { upsert(it) }}
    }

}
