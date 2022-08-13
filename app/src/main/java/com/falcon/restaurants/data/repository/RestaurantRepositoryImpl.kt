package com.falcon.restaurants.data.repository

import android.annotation.SuppressLint
import com.falcon.restaurants.data.mapper.RestaurantDataMapper
import com.falcon.restaurants.data.net.RetrofitInterface
import com.falcon.restaurants.data.db.dao.RestaurantDataDao
import com.falcon.restaurants.domain.model.Restaurant
import com.falcon.restaurants.domain.repository.RestaurantRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class RestaurantRepositoryImpl @Inject constructor(
    val restaurantDataDao: RestaurantDataDao,
    val retrofitInterface: RetrofitInterface,
    var restaurantDataMapper: RestaurantDataMapper

    ) : RestaurantRepository{

    val TAG: String = "RestaurantRepositoryImpl"

    @SuppressLint("CheckResult")
    override fun fetchAndUpsert(): Completable {

        return Completable.defer {
            val maxUpdatedAt: String = restaurantDataDao.getMaxUpdated()
            // this is for test
            //val maxUpdatedAt: String = "1970-01-01 00:00:01"
            retrofitInterface.getRestaurantDtos(maxUpdatedAt)
                             .flatMapCompletable {
                                     restaurantDtos -> upsert(restaurantDataMapper.dtoToDomainList(restaurantDtos))
                             }
        }.subscribeOn(Schedulers.io())
    }

    override fun getByParentId(parent_id: String): Observable<List<Restaurant>> =
        restaurantDataDao.getByParentId(parent_id).map {
            restaurantModels -> restaurantDataMapper.dataToDomainList(restaurantModels)
        }

    override fun hasChildren(id: String): Single<Boolean> = restaurantDataDao.hasChildren(id)

    override fun upsert(restaurant: Restaurant): Long = restaurantDataDao.upsert(
        restaurantDataMapper.domainToData(restaurant)
    )

    override fun upsert(restaurants: List<Restaurant>): Completable {
        return Completable.fromAction { restaurants.map { upsert(it) }
        }
    }

}
