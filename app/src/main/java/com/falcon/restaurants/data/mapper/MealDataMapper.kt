package com.falcon.restaurants.data.mapper

import com.falcon.restaurants.data.net.model.MealDto
import com.falcon.restaurants.data.db.model.MealData
import com.falcon.restaurants.domain.model.Meal
import javax.inject.Inject

class MealDataMapper @Inject constructor() {

    fun dataToDomain(model: MealData): Meal {
        return Meal(
            id = model.id,
            name = model.name,
            details = model.details,
            imageUrl = model.imageUrl,
            restaurantId = model.restaurantId,
            updatedAt = model.updatedAt,
            active = model.active,
            favorite = model.favorite
        )
    }

    fun domainToData(model: Meal): MealData {
        return MealData(
            id = model.id,
            name = model.name,
            details = model.details,
            imageUrl = model.imageUrl,
            restaurantId = model.restaurantId,
            updatedAt = model.updatedAt,
            active = model.active,
            favorite = model.favorite
        )
    }


    fun dataToDomainList (models: List<MealData>): List<Meal> {
        return models.map { dataToDomain(it) }
    }

    fun dtoToDomain(model: MealDto): Meal {
        return Meal(
            id = model.id,
            name = model.name,
            details = model.details,
            imageUrl = model.imageUrl,
            restaurantId = model.restaurantId,
            updatedAt = model.updatedAt,
            active = model.active,
            favorite = model.favorite
        )
    }

    fun dtoToDomainList (models: List<MealDto>): List<Meal> {
        return models.map { dtoToDomain(it) }
    }

//    fun dtoToData(model: MealDto): MealData {
//        return MealData(
//            id = model.id,
//            name = model.name,
//            details = model.details,
//            imageUrl = model.imageUrl,
//            restaurantId = model.restaurantId,
//            updatedAt = model.updatedAt,
//            active = model.active,
//            favorite = model.favorite
//        )
//    }
//
//    fun dtoToDataList (models: List<MealDto>): List<MealData> {
//        return models.map { dtoToData(it) }
//    }
}