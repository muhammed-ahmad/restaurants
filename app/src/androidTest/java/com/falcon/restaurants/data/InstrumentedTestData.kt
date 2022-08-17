package com.falcon.restaurants.data

import com.falcon.restaurants.data.db.model.MealData
import com.falcon.restaurants.data.db.model.RestaurantData


object InstrumentedTestData {

    fun createRestaurantDatas(): MutableList<RestaurantData> = mutableListOf(
        RestaurantData("id1", "0", "name1",
            "image_url", "1", "2020-01-01 00:00:00"),
        RestaurantData("id2", "0", "name2",
            "image_url", "1", "2021-01-01 00:00:00"),
        RestaurantData("id3", "0", "name3",
            "image_url", "1", "2022-01-01 00:00:00")

    )

    fun createMealDatas(): MutableList<MealData> = mutableListOf(
        MealData("id1", "name1", "detail1", "image_url", "id2",
            "2020-01-01 00:00:00", "1", "favorite"),
        MealData("id2", "name2", "detail2", "image_url", "id1",
            "2021-01-01 00:00:00", "1", "favorite"),
        MealData("id3", "name3", "detail3", "image_url", "id2",
            "2022-01-01 00:00:00", "1", "favorite")
    )
}
