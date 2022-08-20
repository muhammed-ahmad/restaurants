package com.falcon.restaurants.domain.interactor

import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.model.Restaurant

object DomainTestData {

    // Restaurants
    fun createRestaurant1() = Restaurant("id1", "0", "name1","image_url", "1", "updated_at")
    fun createRestaurant2() = Restaurant("id2", "0", "name2","image_url", "1", "updated_at")
    fun createRestaurant3() = Restaurant("id3", "0", "name3","image_url", "1", "updated_at")

    fun createRestaurants(): MutableList<Restaurant> = mutableListOf(
        createRestaurant1(), createRestaurant2(), createRestaurant3()
    )

    // Meals
    fun createMeal1() = Meal("id1", "name1", "detail1", "image_url", "id2",
        "updated_at", "active", "favorite")
    fun createMeal2() = Meal("id2", "name2", "detail2", "image_url", "id1",
        "updated_at", "active", "favorite")
    fun createMeal3() = Meal("id3", "name3", "detail3", "image_url", "id2",
        "updated_at", "active", "favorite")

    fun createMeals(): MutableList<Meal> = mutableListOf(
        createMeal1(), createMeal2(), createMeal3()
    )
}