package com.falcon.restaurants.testdata

import com.falcon.restaurants.data.network.restaurant.RestaurantNet

object RestaurantTestData {

    fun createRestaurantNets(): MutableList<RestaurantNet> = mutableListOf(
       RestaurantNet("id1", "0", "name1",
                "image_url", "active", "updated_at"),
       RestaurantNet("id2", "0", "name2",
                "image_url", "active", "updated_at"),
        RestaurantNet("id3", "0", "name3",
                "image_url", "active", "updated_at")
    )

}
