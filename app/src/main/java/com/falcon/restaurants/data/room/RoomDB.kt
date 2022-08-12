package com.falcon.restaurants.data.room
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.falcon.restaurants.data.room.restaurant.RestaurantData
import com.falcon.restaurants.data.room.restaurant.RestaurantDataDao
import com.falcon.restaurants.data.room.meal.MealData
import com.falcon.restaurants.data.room.meal.MealDataDao

@Database(entities = [MealData::class, RestaurantData::class], version = 1)
abstract class RoomDB : RoomDatabase() {

    abstract fun mealDao(): MealDataDao
    abstract fun restaurantDao(): RestaurantDataDao

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