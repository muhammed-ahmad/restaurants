package com.falcon.restaurants.data.network.meal;

data class MealNet(
    val id: String,
    val name : String,
    val details: String,
    val imageUrl: String,
    val restaurantId: String,
    val updatedAt: String,
    val active: String,
    val favorite: String
)
