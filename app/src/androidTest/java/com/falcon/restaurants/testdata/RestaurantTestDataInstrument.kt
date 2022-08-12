package com.falcon.restaurants.testdata

import com.falcon.restaurants.data.room.restaurant.RestaurantData

object RestaurantTestDataInstrument {

    fun createRestaurants(): MutableList<RestaurantData> = mutableListOf(
        RestaurantData("id1", "0", "name1",
            "image_url", "1", "1970-01-01 00:00:03"),
        RestaurantData("id2", "0", "name2",
            "image_url", "1", "1970-01-01 00:00:04"),
        RestaurantData("id3", "0", "name3",
            "image_url", "1", "1970-01-01 00:00:05")

    )
}
