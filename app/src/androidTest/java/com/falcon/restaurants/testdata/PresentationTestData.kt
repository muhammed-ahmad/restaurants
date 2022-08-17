//package com.falcon.restaurants.presentation
//
//import com.falcon.restaurants.data.db.model.MealData
//import com.falcon.restaurants.data.net.model.RestaurantDto
//
//object PresentationTestData {
//
//    fun getMainMeals(): MutableList<MealData> = mutableListOf(
//        MealData("id1", "name1", "details1", "https",
//            "type", "updated_at", "active", "favorite"),
//        MealData("id2", "name2", "details2", "https",
//            "type", "updated_at", "active", "favorite"),
//        MealData("id3", "name3", "details3", "https",
//            "type", "updated_at", "active", "favorite")
//    )
//
//    fun createRestaurantNets(): MutableList<RestaurantDto> = mutableListOf(
//        RestaurantDto("id1", "0", "name1",
//            "image_url", "active", "updated_at"),
//        RestaurantDto("id2", "0", "name2",
//            "image_url", "active", "updated_at"),
//        RestaurantDto("id3", "0", "name3",
//            "image_url", "active", "updated_at")
//    )
//}