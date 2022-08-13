package com.falcon.restaurants.testdata

import com.falcon.restaurants.data.net.model.RestaurantDto

object RestaurantTestData {

    fun createRestaurantNets(): MutableList<RestaurantDto> = mutableListOf(
       RestaurantDto("id1", "0", "name1",
                "image_url", "active", "updated_at"),
       RestaurantDto("id2", "0", "name2",
                "image_url", "active", "updated_at"),
        RestaurantDto("id3", "0", "name3",
                "image_url", "active", "updated_at")
    )

}
