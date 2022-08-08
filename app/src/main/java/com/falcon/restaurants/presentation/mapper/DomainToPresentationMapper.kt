package com.falcon.restaurants.presentation.mapper

import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.presentation.model.MealUiModel

interface DomainToPresentationMapper <D, P> {
    fun toPresentation(model: D): P
    fun toPresentationList(models: List<D>): List<P>
}