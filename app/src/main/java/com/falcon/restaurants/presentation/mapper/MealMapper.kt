package com.falcon.restaurants.presentation.mapper

import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.presentation.model.MealUiModel
import javax.inject.Inject

class MealMapper @Inject constructor() : DomainToPresentationMapper <Meal, MealUiModel> {

    override fun toPresentation(model: Meal): MealUiModel {
        return MealUiModel(
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

    override fun toPresentationList (models: List<Meal>): List<MealUiModel> {
        val list: ArrayList<MealUiModel> = ArrayList()
        for (model in models) {
            list.add(toPresentation(model))
        }
        return list
    }
}