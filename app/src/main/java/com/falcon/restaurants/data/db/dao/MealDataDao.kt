package com.falcon.restaurants.data.db.dao
import androidx.room.Dao
import androidx.room.Query
import com.falcon.restaurants.data.db.model.MealData
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class MealDataDao : BaseDao<MealData>() {
    
    @Query("SELECT * from MealData WHERE restaurantId=:typeIdV ORDER BY updatedAt ASC")
    abstract fun getMealsByRestaurantId(typeIdV: String): Observable<List<MealData>>

    @Query("SELECT * from MealData WHERE id=:mealIdV")
    abstract fun getMealById(mealIdV: String): Single<MealData>

    // getMaxUpdated
    @Query("SELECT MAX(updatedAt) from MealData WHERE active=1")
    abstract override fun getMaxUpdatedAt(): String

    // *** check exists
    @Query("SELECT EXISTS(SELECT * FROM MealData WHERE id = :idV)")
    abstract fun checkExists(idV: String): Boolean

    override fun checkExists(mealData: MealData): Boolean{
        return checkExists(mealData.id)
    }
    // ***

}
