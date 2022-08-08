package com.falcon.restaurants.domain.model

data class Restaurant (
    val id: String,
    val parentId: String,
    val name: String,
    val imageUrl: String,
    val active: String,
    val updatedAt: String
)