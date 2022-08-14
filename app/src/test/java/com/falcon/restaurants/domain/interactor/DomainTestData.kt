package com.falcon.restaurants.domain.interactor

import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.model.Restaurant

object DomainTestData {

    fun createRestaurants(): MutableList<Restaurant> = mutableListOf(
        Restaurant("id1", "0", "name1",
            "image_url", "active", "updated_at"),
        Restaurant("id2", "0", "name2",
            "image_url", "active", "updated_at"),
        Restaurant("id3", "0", "name3",
            "image_url", "active", "updated_at")
    )

    fun createMeals(): MutableList<Meal> = mutableListOf(
        Meal("id1", "name1", "detail1", "image_url", "restaurantId",
            "updated_at", "active", "favorite"),
        Meal("id2", "name2", "detail2", "image_url", "restaurantId",
            "updated_at", "active", "favorite"),
        Meal("id3", "name3", "detail3", "image_url", "restaurantId",
            "updated_at", "active", "favorite")
    )
}