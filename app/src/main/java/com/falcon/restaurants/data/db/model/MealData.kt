package com.falcon.restaurants.data.db.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = [Index(value = ["id"], unique = true)])
data class MealData (
    @PrimaryKey @NonNull val id: String,
    @NonNull val name: String,
    val details: String,
    val imageUrl: String,
    val restaurantId: String,
    val updatedAt: String,
    val active: String,
    val favorite: String
)