package com.falcon.restaurants.data.room.restaurant
import androidx.room.Dao
import androidx.room.Query
import com.falcon.restaurants.data.room.BaseDao
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class RestaurantDataDao : BaseDao<RestaurantData>() {

    @Query("SELECT * from RestaurantData WHERE parentId=:parent_idV and active=1 ORDER BY name ASC")
    abstract fun getByParentId(parent_idV: String): Observable<List<RestaurantData>>

    // check has children
    @Query("SELECT EXISTS(SELECT * FROM RestaurantData WHERE parentId = :id)")
    abstract fun hasChildren(id: String): Single<Boolean>

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
