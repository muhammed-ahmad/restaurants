package com.falcon.restaurants.data.db.dao
import androidx.room.Dao
import androidx.room.Query
import com.falcon.restaurants.data.db.model.RestaurantData
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class RestaurantDataDao : BaseDao<RestaurantData>() {

    @Query("SELECT * from RestaurantData WHERE active=1 ORDER BY name ASC")
    abstract fun getRestaurants(): Observable<List<RestaurantData>>

    // getMaxUpdated
    @Query("SELECT MAX(updatedAt) from RestaurantData WHERE active=1")
    abstract override fun getMaxUpdatedAt(): String

    // *** check exists
    @Query("SELECT EXISTS(SELECT * FROM RestaurantData WHERE id = :id)")
    abstract fun checkExists(id: String): Boolean

    override fun checkExists(restaurantData: RestaurantData): Boolean{
        return checkExists(restaurantData.id)
    }
    // ***
}
