package com.falcon.restaurants.testdata

import com.falcon.restaurants.room.meal.Meal

object MealsTestData {

    fun getMainMeals(): MutableList<Meal> = mutableListOf(
        Meal("id1", "name1", "details1", "https",
            "type", "updated_at", "active", "favorite"),
        Meal("id2", "name2", "details2", "https",
            "type", "updated_at", "active", "favorite"),
        Meal("id3", "name3", "details3", "https",
            "type", "updated_at", "active", "favorite")
    )

}
