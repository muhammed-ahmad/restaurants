package com.falcon.models.presentation.mapper

import com.falcon.restaurants.domain.model.Restaurant
import com.falcon.restaurants.presentation.mapper.DomainToPresentationMapper
import com.falcon.restaurants.presentation.model.RestaurantUiModel
import javax.inject.Inject

class RestaurantMapper @Inject constructor() :
    DomainToPresentationMapper<Restaurant, RestaurantUiModel> {

    override fun toPresentation(model: Restaurant): RestaurantUiModel {
        return RestaurantUiModel(
            id = model.id,
            parentId = model.parentId,
            name = model.name,
            imageUrl = model.imageUrl,
            active = model.active,
            updatedAt = model.updatedAt,
        )
    }

    override fun toPresentationList (models: List<Restaurant>): List<RestaurantUiModel> {
        return models.map { toPresentation(it) }
    }
}