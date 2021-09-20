package com.falcon.restaurants.room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import com.falcon.restaurants.room.restaurant.Restaurant;
import com.falcon.restaurants.room.restaurant.RestaurantDao;
import com.falcon.restaurants.room.meal.Meal;
import com.falcon.restaurants.room.meal.MealDao;

@Database(entities = {Meal.class, Restaurant.class}, version = 1)
public abstract class RoomDB extends RoomDatabase {

    public abstract MealDao mealDao();
    public abstract RestaurantDao restaurantDao();

    private static volatile RoomDB INSTANCE;

    public static RoomDB getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDB.class, "restaurants.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}