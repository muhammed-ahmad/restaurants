package com.falcon.restaurants.testdata

import com.falcon.restaurants.data.room.meal.MealData

object MealsTestData {

    fun getMainMeals(): MutableList<MealData> = mutableListOf(
        MealData("id1", "name1", "details1", "https",
            "type", "updated_at", "active", "favorite"),
        MealData("id2", "name2", "details2", "https",
            "type", "updated_at", "active", "favorite"),
        MealData("id3", "name3", "details3", "https",
            "type", "updated_at", "active", "favorite")
    )

}
