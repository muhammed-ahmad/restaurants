package com.falcon.restaurants.data.net.model;

data class MealDto(
    val id: String,
    val name : String,
    val details: String,
    val imageUrl: String,
    val restaurantId: String,
    val updatedAt: String,
    val active: String,
    val favorite: String
)
