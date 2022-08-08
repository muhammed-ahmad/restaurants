package com.falcon.restaurants.testdata

import com.falcon.restaurants.data.room.restaurant.RestaurantModel

object RestaurantTestDataInstrument {

    fun createRestaurants(): MutableList<RestaurantModel> = mutableListOf(
        RestaurantModel("id1", "0", "name1",
            "image_url", "1", "1970-01-01 00:00:03"),
        RestaurantModel("id2", "0", "name2",
            "image_url", "1", "1970-01-01 00:00:04"),
        RestaurantModel("id3", "0", "name3",
            "image_url", "1", "1970-01-01 00:00:05")

    )
}
