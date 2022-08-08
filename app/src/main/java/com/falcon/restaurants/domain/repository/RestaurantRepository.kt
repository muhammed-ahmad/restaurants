package com.falcon.restaurants.domain.repository

import com.falcon.restaurants.data.network.restaurant.RestaurantNet
import com.falcon.restaurants.data.room.restaurant.RestaurantModel
import com.falcon.restaurants.domain.model.Restaurant
import io.reactivex.Observable
import io.reactivex.Single

interface RestaurantRepository {

    fun fetch(): Observable<String>

    fun getByParentId(parent_id: String): Observable<List<Restaurant>>

    fun hasChildren(id: String): Single<Boolean>

    fun upsert(restaurantNet: RestaurantNet): Long

    fun upsert(restaurantNets: List<RestaurantNet>)

}
