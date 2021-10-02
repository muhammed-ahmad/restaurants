package com.falcon.restaurants.room.meal

import androidx.room.Dao
import androidx.room.Query
import com.falcon.restaurants.room.BaseDao
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class MealDao : BaseDao<Meal>() {
    
    @Query("SELECT * from Meal WHERE restaurantId=:typeIdV ORDER BY updatedAt ASC")
    abstract fun getByRestaurantId(typeIdV: String): Observable<List<Meal>>

    @Query("SELECT * from Meal WHERE id=:mealIdV")
    abstract fun getMealById(mealIdV: String): Single<Meal> 

    //*** getMaxUpdated
    @Query("SELECT MAX(updatedAt) from Meal WHERE active=1")
    abstract override fun getMaxUpdatedAt(): String

    //**** check exists
    @Query("SELECT EXISTS(SELECT * FROM Meal WHERE id = :idV)")
    abstract fun checkExists(idV: String): Single<Boolean>

   override fun checkExists(meal: Meal): Boolean{
        return checkExists(meal.id).blockingGet()
    }

}
