package com.falcon.restaurants.domain.repository

import com.falcon.restaurants.data.network.restaurant.RestaurantDto
import com.falcon.restaurants.domain.model.Restaurant
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface RestaurantRepository {

    fun fetchAndUpsert(): Completable

    fun getByParentId(parent_id: String): Observable<List<Restaurant>>

    fun hasChildren(id: String): Single<Boolean>

    fun upsert(restaurant: Restaurant): Long

    fun upsert(restaurants: List<Restaurant>): Completable

}
