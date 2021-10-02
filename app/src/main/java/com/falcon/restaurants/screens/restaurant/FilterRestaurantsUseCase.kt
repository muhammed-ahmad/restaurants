package com.falcon.restaurants.screens.restaurant

import com.falcon.restaurants.base.FilterBase
import com.falcon.restaurants.room.restaurant.Restaurant

import javax.inject.Inject

class FilterRestaurantsUseCase @Inject constructor(): FilterBase<Restaurant>() {

    override fun isQueryFound(query: String, current: Restaurant): Boolean {
        return current.name.lowercase().contains(query.lowercase())
    }
}
