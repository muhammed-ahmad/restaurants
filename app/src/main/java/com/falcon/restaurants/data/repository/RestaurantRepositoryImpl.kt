package com.falcon.restaurants.data.repository

import android.annotation.SuppressLint
import com.falcon.restaurants.data.mapper.RestaurantModelMapper
import com.falcon.restaurants.data.network.RetrofitInterface
import com.falcon.restaurants.data.network.restaurant.RestaurantNet
import com.falcon.restaurants.data.room.restaurant.RestaurantModel
import com.falcon.restaurants.data.room.restaurant.RestaurantModelDao
import com.falcon.restaurants.domain.model.Restaurant
import com.falcon.restaurants.domain.repository.RestaurantRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class RestaurantRepositoryImpl @Inject constructor(
    val restaurantModelDao: RestaurantModelDao,
    val retrofitInterface: RetrofitInterface,
    var restaurantModelMapper: RestaurantModelMapper

    ) : RestaurantRepository{

    val TAG: String = "RestaurantRepositoryImpl"

    @SuppressLint("CheckResult")
    override fun fetchAndUpsert(): Completable {

        return Completable.defer {
            val maxUpdatedAt: String = restaurantModelDao.getMaxUpdated()
            // this is for test
            //val maxUpdatedAt: String = "1970-01-01 00:00:01"
            retrofitInterface.getRestaurants(maxUpdatedAt)
                             .flatMapCompletable { restaurantNets -> upsert(restaurantNets) }
        }.subscribeOn(Schedulers.io())
    }

    override fun getByParentId(parent_id: String): Observable<List<Restaurant>> =
        restaurantModelDao.getByParentId(parent_id).map {
            restaurantModels -> restaurantModelMapper.toDomainList(restaurantModels)
        }

    override fun hasChildren(id: String): Single<Boolean> = restaurantModelDao.hasChildren(id)

    override fun upsert(restaurantNet: RestaurantNet): Long = restaurantModelDao.upsert(
        RestaurantModel(
                restaurantNet.id,
                restaurantNet.parentId,
                restaurantNet.name,
                restaurantNet.imageUrl,
                restaurantNet.active,
                restaurantNet.updatedAt)
        )

    override fun upsert(restaurantNets: List<RestaurantNet>): Completable {
        return Completable.fromAction {
            for (restaurantCurrent in restaurantNets) {
                val rowId: Long = upsert(restaurantCurrent)
            }
        }
    }

}
