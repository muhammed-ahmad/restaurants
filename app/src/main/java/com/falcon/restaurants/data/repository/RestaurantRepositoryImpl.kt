package com.falcon.restaurants.data.repository

import android.annotation.SuppressLint
import com.falcon.restaurants.data.mapper.RestaurantModelMapper
import com.falcon.restaurants.data.network.restaurant.FetchRestaurantsEndPoint
import com.falcon.restaurants.data.network.restaurant.RestaurantNet
import com.falcon.restaurants.data.room.restaurant.RestaurantModel
import com.falcon.restaurants.data.room.restaurant.RestaurantModelDao
import com.falcon.restaurants.domain.model.Restaurant
import com.falcon.restaurants.domain.repository.RestaurantRepository
import com.falcon.restaurants.domain.utils.Logger
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(
    val restaurantModelDao: RestaurantModelDao,
    val fetchRestaurantsEndPoint: FetchRestaurantsEndPoint,
    var restaurantModelMapper: RestaurantModelMapper
    ) : RestaurantRepository{

    val TAG: String = "RestaurantRepositoryImpl"

    @SuppressLint("CheckResult")
    override fun fetch(): Observable<String>{

        return Observable.create{
                emitter ->
                    val maxUpdatedAt: String = restaurantModelDao.getMaxUpdated()
                    //Logger.log(TAG, maxUpdatedAt)

                    // this is for test
                    //val maxUpdatedAt: String = "1970-01-01 00:00:01"
                    //////

                    fetchRestaurantsEndPoint.fetch(maxUpdatedAt, object: FetchRestaurantsEndPoint.Listener {

                        override fun onFetchSuccess(restaurantNets: List<RestaurantNet>) {
                            Logger.log(TAG , " onFetchSuccess: " + restaurantNets.size)
                            upsert(restaurantNets)
                            emitter.onNext("upsert_completed")
                            emitter.onComplete()
                        }

                        override fun onFetchFailed(e: Throwable) {
                            Logger.log(TAG, "onFetchFailed: error: " + e.getLocalizedMessage())
                            emitter.onError(e)
                        }
                    })
            }
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

    override fun upsert(restaurantNets: List<RestaurantNet>) {
        for (restaurantCurrent in restaurantNets) {
            val rowId: Long = upsert(restaurantCurrent)
        }
    }

}
