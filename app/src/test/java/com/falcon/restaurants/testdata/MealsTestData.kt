package com.falcon.restaurants.testdata

import com.falcon.restaurants.data.room.meal.MealModel

object MealsTestData {

    fun getMainMeals(): MutableList<MealModel> = mutableListOf(
        MealModel("id1", "name1", "details1", "https",
            "type", "updated_at", "active", "favorite"),
        MealModel("id2", "name2", "details2", "https",
            "type", "updated_at", "active", "favorite"),
        MealModel("id3", "name3", "details3", "https",
            "type", "updated_at", "active", "favorite")
    )

}
