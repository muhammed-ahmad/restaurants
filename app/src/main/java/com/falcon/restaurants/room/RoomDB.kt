package com.falcon.restaurants.room
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.falcon.restaurants.room.restaurant.Restaurant
import com.falcon.restaurants.room.restaurant.RestaurantDao
import com.falcon.restaurants.room.meal.Meal
import com.falcon.restaurants.room.meal.MealDao

@Database(entities = [Meal::class, Restaurant::class], version = 1)
abstract class RoomDB : RoomDatabase() {

    abstract fun mealDao(): MealDao
    abstract fun restaurantDao(): RestaurantDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getInstance(context: Context): RoomDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                                        context.applicationContext,
                                        RoomDB::class.java,
                                        "restaurants.db"
                                     )
                                     .build()
                INSTANCE = instance
                instance
            }
        }
    }
}