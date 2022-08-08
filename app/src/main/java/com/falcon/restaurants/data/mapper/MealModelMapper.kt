package com.falcon.restaurants.data.mapper

import com.falcon.restaurants.data.room.meal.MealModel
import com.falcon.restaurants.domain.model.Meal
import javax.inject.Inject

class MealModelMapper @Inject constructor() : DataToDomainMapper <MealModel, Meal> {

    override fun toDomain(model: MealModel): Meal {
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

    override fun toDomainList (models: List<MealModel>): List<Meal> {
        val list: ArrayList<Meal> = ArrayList()
        for (model in models) {
            list.add(toDomain(model))
        }
        return list
    }
}