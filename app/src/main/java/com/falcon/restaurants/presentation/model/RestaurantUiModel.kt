package com.falcon.restaurants.presentation.model

data class RestaurantUiModel (
    val id: String,
    val parentId: String,
    val name: String,
    val imageUrl: String,
    val active: String,
    val updatedAt: String
)