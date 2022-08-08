package com.falcon.restaurants.data.mapper

import com.falcon.restaurants.data.room.restaurant.RestaurantModel
import com.falcon.restaurants.domain.model.Restaurant
import javax.inject.Inject

class RestaurantModelMapper @Inject constructor() :
    DataToDomainMapper<RestaurantModel, Restaurant> {

    override fun toDomain(model: RestaurantModel): Restaurant {
        return Restaurant(
            id = model.id,
            parentId = model.parentId,
            name = model.name,
            imageUrl = model.imageUrl,
            active = model.active,
            updatedAt = model.updatedAt,
        )
    }

    override fun toDomainList (models: List<RestaurantModel>): List<Restaurant> {
        val list: ArrayList<Restaurant> = ArrayList()
        for (model in models) {
            list.add(toDomain(model))
        }
        return list
    }
}