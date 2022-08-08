package com.falcon.restaurants.data.room.meal
import androidx.room.Dao
import androidx.room.Query
import com.falcon.restaurants.data.room.BaseDao
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class MealModelDao : BaseDao<MealModel>() {
    
    @Query("SELECT * from MealModel WHERE restaurantId=:typeIdV ORDER BY updatedAt ASC")
    abstract fun getByRestaurantId(typeIdV: String): Observable<List<MealModel>>

    @Query("SELECT * from MealModel WHERE id=:mealIdV")
    abstract fun getMealById(mealIdV: String): Single<MealModel>

    // getMaxUpdated
    @Query("SELECT MAX(updatedAt) from MealModel WHERE active=1")
    abstract override fun getMaxUpdatedAt(): String

    // *** check exists
    @Query("SELECT EXISTS(SELECT * FROM MealModel WHERE id = :idV)")
    abstract fun checkExists(idV: String): Boolean

    override fun checkExists(mealModel: MealModel): Boolean{
        return checkExists(mealModel.id)
    }
    // ***

}
