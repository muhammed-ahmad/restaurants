package com.falcon.restaurants.data.repository
import com.falcon.restaurants.data.mapper.MealDataMapper
import com.falcon.restaurants.data.net.RetrofitInterface
import com.falcon.restaurants.data.db.dao.MealDataDao
import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.repository.MealRepository
import io.reactivex.Completable
import javax.inject.Inject
import io.reactivex.Observable
import io.reactivex.Single

class MealRepositoryImpl @Inject constructor (
    val mealDataDao: MealDataDao,
    val retrofitInterface: RetrofitInterface,
    val mealDataMapper: MealDataMapper

    ) : MealRepository {

    val TAG: String = "MealRepositoryImpl"

    override fun getMealsByRestaurantId(typeIdV: String): Observable<List<Meal>> {
        return mealDataDao.getMealsByRestaurantId(typeIdV).map {
                mealDatas -> mealDataMapper.dataToDomainList(mealDatas)
         }
    }

    override fun getMealById(mealId: String): Single<Meal> {
        return mealDataDao.getMealById(mealId).map {
            mealData -> mealDataMapper.dataToDomain(mealData)
        }
    }

    override fun upsert(meal: Meal) {
            mealDataDao.upsert(mealDataMapper.domainToData(meal))
    }

    override fun upsert(meals: List<Meal>) : Completable{
        return Completable.fromAction { meals.map { upsert(it) }}
    }

    override fun fetchMeals(): Single<List<Meal>> {
        val maxUpdatedAt: String = mealDataDao.getMaxUpdated()
        // may use this for test: val maxUpdatedAt: String = "1970-01-01 00:00:01"
        return retrofitInterface.fetchMealDtos(maxUpdatedAt).map {
                mealDtos -> mealDataMapper.dtoToDomainList(mealDtos)
        }
    }

}
