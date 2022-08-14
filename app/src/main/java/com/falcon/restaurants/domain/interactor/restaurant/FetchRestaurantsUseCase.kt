package com.falcon.restaurants.domain.interactor.restaurant

import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.model.Restaurant
import com.falcon.restaurants.domain.repository.RestaurantRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class FetchRestaurantsUseCase @Inject constructor(
    private val restaurantRepository: RestaurantRepository
    ){

    fun execute(): Single<List<Restaurant>> = restaurantRepository.fetchRestaurants()

}
