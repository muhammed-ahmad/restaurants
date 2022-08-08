package com.falcon.restaurants.data.room.restaurant
import androidx.room.Dao
import androidx.room.Query
import com.falcon.restaurants.data.room.BaseDao
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class RestaurantModelDao : BaseDao<RestaurantModel>() {

    @Query("SELECT * from RestaurantModel WHERE parentId=:parent_idV and active=1 ORDER BY name ASC")
    abstract fun getByParentId(parent_idV: String): Observable<List<RestaurantModel>>

    // check has children
    @Query("SELECT EXISTS(SELECT * FROM RestaurantModel WHERE parentId = :id)")
    abstract fun hasChildren(id: String): Single<Boolean>

    // getMaxUpdated
    @Query("SELECT MAX(updatedAt) from RestaurantModel WHERE active=1")
    abstract override fun getMaxUpdatedAt(): String

    // *** check exists
    @Query("SELECT EXISTS(SELECT * FROM RestaurantModel WHERE id = :id)")
    abstract fun checkExists(id: String): Boolean

    override fun checkExists(restaurantModel: RestaurantModel): Boolean{
        return checkExists(restaurantModel.id)
    }
    // ***
}
