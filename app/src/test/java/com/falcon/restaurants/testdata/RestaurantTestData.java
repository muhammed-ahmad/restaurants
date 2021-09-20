package com.falcon.restaurants.testdata;

import com.falcon.restaurants.network.restaurant.RestaurantNet;
import com.falcon.restaurants.room.restaurant.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantTestData {

    public static List<RestaurantNet> createRestaurantNets() {
        List<RestaurantNet> restaurants = new ArrayList<>();

        RestaurantNet restaurant1 = new RestaurantNet("id1", "0", "name1",
                "image_url", "active", "updated_at");
        RestaurantNet restaurant2 = new RestaurantNet("id2", "0", "name2",
                "image_url", "active", "updated_at");
        RestaurantNet restaurant3 = new RestaurantNet("id3", "0", "name3",
                "image_url", "active", "updated_at");

        restaurants.add(restaurant1);
        restaurants.add(restaurant2);
        restaurants.add(restaurant3);
        return restaurants;
    }

    public static Restaurant createRestaurant() {
        Restaurant restaurant1 = new Restaurant("id1", "0", "name1",
                "image_url", "active", "updated_at");
        return restaurant1;
    }
}
