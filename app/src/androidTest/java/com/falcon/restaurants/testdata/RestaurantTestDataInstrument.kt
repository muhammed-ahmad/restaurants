package com.falcon.restaurants.testdata

import com.falcon.restaurants.room.restaurant.Restaurant

object RestaurantTestDataInstrument {

    fun createRestaurants(): MutableList<Restaurant> = mutableListOf(
        Restaurant("id1", "0", "name1",
            "image_url", "1", "1970-01-01 00:00:03"),
        Restaurant("id2", "0", "name2",
            "image_url", "1", "1970-01-01 00:00:04"),
        Restaurant("id3", "0", "name3",
            "image_url", "1", "1970-01-01 00:00:05")

    )
}
