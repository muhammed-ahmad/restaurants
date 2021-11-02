package com.falcon.restaurants.room.restaurant
import androidx.room.Dao
import androidx.room.Query
import com.falcon.restaurants.room.BaseDao
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class RestaurantDao : BaseDao<Restaurant>() {

    @Query("SELECT * from Restaurant WHERE parentId=:parent_idV and active=1 ORDER BY name ASC")
    abstract fun getByParentId(parent_idV: String): Observable<List<Restaurant>>

    // check has children
    @Query("SELECT EXISTS(SELECT * FROM Restaurant WHERE parentId = :id)")
    abstract fun hasChildren(id: String): Single<Boolean>

    // getMaxUpdated
    @Query("SELECT MAX(updatedAt) from Restaurant WHERE active=1")
    abstract override fun getMaxUpdatedAt(): String

    // *** check exists
    @Query("SELECT EXISTS(SELECT * FROM Restaurant WHERE id = :id)")
    abstract fun checkExists(id: String): Boolean

    override fun checkExists(restaurant: Restaurant): Boolean{
        return checkExists(restaurant.id)
    }
    // ***
}
