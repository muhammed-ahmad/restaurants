package com.falcon.restaurants.screens.restaurant

import android.annotation.SuppressLint
import com.falcon.restaurants.network.restaurant.FetchRestaurantsEndPoint
import com.falcon.restaurants.network.restaurant.RestaurantNet
import com.falcon.restaurants.room.restaurant.Restaurant
import com.falcon.restaurants.room.restaurant.RestaurantDao
import com.falcon.restaurants.utils.Logger
import javax.inject.Inject
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single
import io.reactivex.annotations.NonNull
import java.lang.Exception

class FetchRestaurantsUseCase @Inject constructor(val restaurantDao: RestaurantDao,
                                          val fetchRestaurantsEndPoint: FetchRestaurantsEndPoint){

    val TAG: String = "FetchRestaurantsUseCase"

    @SuppressLint("CheckResult")
    fun fetch(): Observable<String>{

        return Observable.create(object: ObservableOnSubscribe<String>{

            @Throws(Exception::class)
            override fun subscribe(@NonNull emitter: ObservableEmitter<String>) {

                val maxUpdatedAt: String = restaurantDao.getMaxUpdated()
                //Logger.log(TAG, maxUpdatedAt)

                // this is for test
                //val maxUpdatedAt: String = "1970-01-01 00:00:01"
                //////

                fetchRestaurantsEndPoint.fetch(maxUpdatedAt, object: FetchRestaurantsEndPoint.Listener {

                    override fun onFetchSuccess(restaurantNets: List<RestaurantNet>) {
                        System.out.println(TAG + " onFetchSuccess: " + restaurantNets.size)
                        upsert(restaurantNets)
                        emitter.onNext("upsert_completed")
                        emitter.onComplete()
                    }

                    override fun onFetchFailed(e: Throwable) {
                        System.out.println(TAG + "onFetchFailed: error: " + e.getLocalizedMessage())
                        emitter.onError(e)
                    }
                })
            }
        })
    }

    fun getByParentId(parent_id: String): Observable<List<Restaurant>>  {
        return restaurantDao.getByParentId(parent_id)
    }

    fun hasChildren(id: String): Single<Boolean> {
        return restaurantDao.hasChildren(id)
    }

    fun upsert(restaurantNet: RestaurantNet): Long {
        return restaurantDao.upsert(Restaurant(
                restaurantNet.id,
                restaurantNet.parentId,
                restaurantNet.name,
                restaurantNet.imageUrl,
                restaurantNet.active,
                restaurantNet.updatedAt
        ))
    }

    fun upsert(restaurantNets: List<RestaurantNet>) {
        for (restaurantCurrent in restaurantNets) {
            val rowId: Long = upsert(restaurantCurrent)
        }
        notifyInsertSuccess()
    }

    fun notifyInsertSuccess() {
    }

}
