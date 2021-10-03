package com.falcon.restaurants.testdata

import com.falcon.restaurants.network.restaurant.RestaurantNet
import com.falcon.restaurants.room.restaurant.Restaurant

object RestaurantTestData {

    fun createRestaurantsForRoom(): MutableList<Restaurant> {
        val restaurants: MutableList<Restaurant> = ArrayList()

        val restaurant1: Restaurant = Restaurant("id1", "0", "name1",
                "image_url", "1", "1970-01-01 00:00:03")
        val restaurant2: Restaurant = Restaurant("id2", "0", "name2",
                "image_url", "1", "1970-01-01 00:00:04")
        val restaurant3: Restaurant = Restaurant("id3", "0", "name3",
                "image_url", "1", "1970-01-01 00:00:05")

        restaurants.add(restaurant1)
        restaurants.add(restaurant2)
        restaurants.add(restaurant3)
        return restaurants
    }

    fun createRestaurantNets(): MutableList<RestaurantNet>{
        val restaurants: MutableList<RestaurantNet> = ArrayList()

        val restaurant1: RestaurantNet = RestaurantNet("id1", "0", "name1",
                "image_url", "active", "updated_at")
        val restaurant2: RestaurantNet = RestaurantNet("id2", "0", "name2",
                "image_url", "active", "updated_at")
        val restaurant3: RestaurantNet = RestaurantNet("id3", "0", "name3",
                "image_url", "active", "updated_at")

        restaurants.add(restaurant1)
        restaurants.add(restaurant2)
        restaurants.add(restaurant3)
        return restaurants
    }

    fun createRestaurant(): Restaurant {
        val restaurant1: Restaurant = Restaurant("id1", "0", "name1",
                "image_url", "active", "updated_at")
        return restaurant1
    }
}
