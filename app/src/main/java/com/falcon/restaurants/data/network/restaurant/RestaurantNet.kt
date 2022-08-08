package com.falcon.restaurants.data.network.restaurant

data class RestaurantNet(
    val id: String,
    val parentId: String,
    val name: String,
    val imageUrl: String,
    val active: String,
    val updatedAt: String
)