package com.falcon.restaurants.domain.model

data class Meal (
    val id: String,
    val name: String,
    val details: String,
    val imageUrl: String,
    val restaurantId: String,
    val updatedAt: String,
    val active: String,
    val favorite: String
)