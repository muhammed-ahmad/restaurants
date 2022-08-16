package com.falcon.restaurants.data

import com.falcon.restaurants.data.db.model.MealData
import com.falcon.restaurants.data.db.model.RestaurantData
import com.falcon.restaurants.data.net.model.MealDto
import com.falcon.restaurants.data.net.model.RestaurantDto
import com.falcon.restaurants.domain.interactor.DomainTestData
import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.model.Restaurant

object DataTestData {

    // ************************** Meal

    // Meal
    fun createMeal1() = Meal("id1", "name1", "details1", "https",
        "type", "updated_at", "active", "favorite")

    // MealData
    fun createMealData1() = MealData("id1", "name1", "details1", "https",
        "type", "updated_at", "active", "favorite")
    fun createMealData2() = MealData("id2", "name2", "details2", "https",
        "type", "updated_at", "active", "favorite")
    fun createMealData3() = MealData("id3", "name3", "details3", "https",
        "type", "updated_at", "active", "favorite")

    fun createMealDatas(): MutableList<MealData> = mutableListOf(
        createMealData1(),
        createMealData2(),
        createMealData3()
    )

    // MealDto
    fun createMealDto1() = MealDto("id1", "name1", "details1", "https",
        "type", "updated_at", "active", "favorite")
    fun createMealDto2() = MealDto("id2", "name2", "details2", "https",
        "type", "updated_at", "active", "favorite")
    fun createMealDto3() = MealDto("id3", "name3", "details3", "https",
        "type", "updated_at", "active", "favorite")

    fun createMealDtos(): MutableList<MealDto> = mutableListOf(
        createMealDto1(),
        createMealDto2(),
        createMealDto3()
    )

    // ************************** Restaurant

    // Meal
    fun createRestaurant1() = Restaurant("id1", "0", "name1",
        "image_url", "active", "updated_at")

    // RestaurantData
    fun createRestaurantData1() = RestaurantData("id1", "0", "name1",
        "image_url", "active", "updated_at")
    fun createRestaurantData2() = RestaurantData("id2", "0", "name2",
        "image_url", "active", "updated_at")
    fun createRestaurantData3() = RestaurantData("id3", "0", "name3",
        "image_url", "active", "updated_at")

    fun createRestaurantDatas(): MutableList<RestaurantData> = mutableListOf(
        createRestaurantData1(),
        createRestaurantData2(),
        createRestaurantData3()
    )

    // MealDto
    fun createRestaurantDto1() = RestaurantDto("id1", "0", "name1",
        "image_url", "active", "updated_at")
    fun createRestaurantDto2() = RestaurantDto("id2", "0", "name2",
        "image_url", "active", "updated_at")
    fun createRestaurantDto3() = RestaurantDto("id3", "0", "name3",
        "image_url", "active", "updated_at")

    fun createRestaurantDtos(): MutableList<RestaurantDto> = mutableListOf(
        createRestaurantDto1(),
        createRestaurantDto2(),
        createRestaurantDto3()
    )

}