package com.falcon.restaurants.testdata;

import com.falcon.restaurants.room.meal.Meal;

import java.util.ArrayList;
import java.util.List;

public class MealsTestData {

    public static List<Meal> getMainMeals() {

        List<Meal> mainMeals = new ArrayList<>();

        Meal meal1 = new Meal("id1", "name1", "details1", "https",
                "type", "updated_at", "active", "favorite");
        Meal meal2 = new Meal("id2", "name2", "details2", "https",
                "type", "updated_at", "active", "favorite");
        Meal meal3 = new Meal("id3", "name3", "details3", "https",
                "type", "updated_at", "active", "favorite");

        mainMeals.add(meal1);
        mainMeals.add(meal2);
        mainMeals.add(meal3);

        return mainMeals;

    }
}
