package com.falcon.restaurants.data.mapper

import com.falcon.restaurants.data.net.model.RestaurantDto
import com.falcon.restaurants.data.db.model.RestaurantData
import com.falcon.restaurants.domain.model.Restaurant
import javax.inject.Inject

class RestaurantDataMapper @Inject constructor() {

    fun dataToDomain(model: RestaurantData): Restaurant {
        return Restaurant(
            id = model.id,
            parentId = model.parentId,
            name = model.name,
            imageUrl = model.imageUrl,
            active = model.active,
            updatedAt = model.updatedAt,
        )
    }

    fun dataToDomainList (models: List<RestaurantData>): List<Restaurant> {
        return models.map { dataToDomain(it) }
    }

    fun domainToData(model: Restaurant): RestaurantData {
        return RestaurantData(
            id = model.id,
            parentId = model.parentId,
            name = model.name,
            imageUrl = model.imageUrl,
            active = model.active,
            updatedAt = model.updatedAt,
        )
    }

    fun dtoToDomain(model: RestaurantDto): Restaurant {
        return Restaurant(
            id = model.id,
            parentId = model.parentId,
            name = model.name,
            imageUrl = model.imageUrl,
            active = model.active,
            updatedAt = model.updatedAt,
        )
    }

    fun dtoToDomainList(models: List<RestaurantDto>): List<Restaurant> {
        return models.map { dtoToDomain(it) }
    }

}