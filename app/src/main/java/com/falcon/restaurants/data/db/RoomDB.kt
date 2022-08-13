package com.falcon.restaurants.data.db
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.falcon.restaurants.data.db.model.RestaurantData
import com.falcon.restaurants.data.db.dao.RestaurantDataDao
import com.falcon.restaurants.data.db.model.MealData
import com.falcon.restaurants.data.db.dao.MealDataDao

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