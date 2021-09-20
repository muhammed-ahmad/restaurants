package com.falcon.restaurants.testdata;

import com.falcon.restaurants.network.restaurant.RestaurantNet;
import com.falcon.restaurants.room.restaurant.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantTestData {

    public static List<RestaurantNet> createRestaurantsNet() {
        List<RestaurantNet> restaurantNets = new ArrayList<>();

        RestaurantNet restaurant1 = new RestaurantNet("id1", "0", "name1",
                "image_url", "1", "1970-01-01 00:00:03");
        RestaurantNet restaurant2 = new RestaurantNet("id2", "0", "name2",
                "image_url", "1", "1970-01-01 00:00:04");
        RestaurantNet restaurant3 = new RestaurantNet("id3", "0", "name3",
                "image_url", "1", "1970-01-01 00:00:05");

        restaurantNets.add(restaurant1);
        //categories.add(restaurant1);
        //categories.add(restaurant1);
        return restaurantNets;
    }

    public static List<Restaurant> createRestaurantsForRoom() {
        List<Restaurant> restaurants = new ArrayList<>();

        Restaurant restaurant1 = new Restaurant("id1", "0", "name1",
                "image_url", "1", "1970-01-01 00:00:03");
        Restaurant restaurant2 = new Restaurant("id2", "0", "name2",
                "image_url", "1", "1970-01-01 00:00:04");
        Restaurant restaurant3 = new Restaurant("id3", "0", "name3",
                "image_url", "1", "1970-01-01 00:00:05");

        restaurants.add(restaurant1);
        restaurants.add(restaurant2);
        restaurants.add(restaurant3);
        return restaurants;
    }

    public static Restaurant createRestaurant() {
        Restaurant restaurant1 = new Restaurant("id1", "0", "name1",
                "image_url", "1", "1970-01-01 00:00:03");
        Restaurant restaurant2 = new Restaurant("id2", "0", "name2",
                "image_url", "1", "1970-01-01 00:00:04");
        Restaurant restaurant3 = new Restaurant("id3", "0", "name3",
                "image_url", "1", "1970-01-01 00:00:05");
        return restaurant1;
    }
}
