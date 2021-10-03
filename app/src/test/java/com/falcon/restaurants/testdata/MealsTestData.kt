package com.falcon.restaurants.testdata;

import com.falcon.restaurants.room.meal.Meal;

object MealsTestData {

    fun getMainMeals(): MutableList<Meal> {

        val mainMeals: MutableList<Meal> = ArrayList()

        val meal1: Meal = Meal("id1", "name1", "details1", "https",
            "type", "updated_at", "active", "favorite")
        val meal2: Meal = Meal("id2", "name2", "details2", "https",
            "type", "updated_at", "active", "favorite")
        val meal3: Meal = Meal("id3", "name3", "details3", "https",
            "type", "updated_at", "active", "favorite")

        mainMeals.add(meal1)
        mainMeals.add(meal2)
        mainMeals.add(meal3)

        return mainMeals

    }

}
