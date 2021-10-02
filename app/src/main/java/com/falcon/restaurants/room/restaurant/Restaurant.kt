package com.falcon.restaurants.room.restaurant;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

//@Entity(indices = arrayOf(Index(value = arrayOf("name"), unique = true)))
@Entity(indices = [Index(value = ["name"], unique = true)])
data class Restaurant (
    @PrimaryKey @NonNull val id: String,
    val parentId: String,
    val name: String,
    val imageUrl: String,
    val active: String,
    val updatedAt: String
)