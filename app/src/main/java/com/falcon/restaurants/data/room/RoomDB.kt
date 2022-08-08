package com.falcon.restaurants.data.room
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.falcon.restaurants.data.room.restaurant.RestaurantModel
import com.falcon.restaurants.data.room.restaurant.RestaurantModelDao
import com.falcon.restaurants.data.room.meal.MealModel
import com.falcon.restaurants.data.room.meal.MealModelDao

@Database(entities = [MealModel::class, RestaurantModel::class], version = 1)
abstract class RoomDB : RoomDatabase() {

    abstract fun mealDao(): MealModelDao
    abstract fun restaurantDao(): RestaurantModelDao

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