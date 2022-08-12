package com.falcon.restaurants.domain.interactor

import com.falcon.restaurants.domain.model.Restaurant
import com.falcon.restaurants.domain.repository.RestaurantRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class FetchRestaurantsUseCase @Inject constructor(
    private val restaurantRepository: RestaurantRepository
    ){

    fun fetchAndUpsert(): Completable = restaurantRepository.fetchAndUpsert()

    fun getByParentId(parent_id: String): Observable<List<Restaurant>> = restaurantRepository.getByParentId(parent_id)

    fun hasChildren(id: String): Single<Boolean> = restaurantRepository.hasChildren(id)

}
