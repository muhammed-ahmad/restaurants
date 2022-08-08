package com.falcon.restaurants.domain.interactor

import com.falcon.restaurants.data.network.restaurant.RestaurantNet
import com.falcon.restaurants.data.room.restaurant.RestaurantModel
import com.falcon.restaurants.domain.model.Restaurant
import com.falcon.restaurants.domain.repository.RestaurantRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class FetchRestaurantsUseCase @Inject constructor(
    private val restaurantRepository: RestaurantRepository
    ){

    fun fetch(): Observable<String> = restaurantRepository.fetch()

    fun getByParentId(parent_id: String): Observable<List<Restaurant>> = restaurantRepository.getByParentId(parent_id)

    fun hasChildren(id: String): Single<Boolean> = restaurantRepository.hasChildren(id)

    fun upsert(restaurantNet: RestaurantNet): Long = restaurantRepository.upsert(restaurantNet)

    fun upsert(restaurantNets: List<RestaurantNet>) = restaurantRepository.upsert(restaurantNets)

}
