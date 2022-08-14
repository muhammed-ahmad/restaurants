package com.falcon.restaurants.domain.interactor.restaurant

import com.falcon.restaurants.domain.model.Restaurant
import com.falcon.restaurants.domain.repository.RestaurantRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class FetchAndUpsertRestaurantsUseCase @Inject constructor(
    private val fetchRestaurantsUseCase: FetchRestaurantsUseCase,
    private val upsertRestaurantsUseCase: UpsertRestaurantsUseCase
    ){

    fun execute(): Completable {
        return Completable.defer {
            fetchRestaurantsUseCase.execute().flatMapCompletable {
                    restaurants -> upsertRestaurantsUseCase.execute(restaurants)
            }
        }
    }

}
