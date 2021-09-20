package com.falcon.restaurants.room.restaurant;

import androidx.room.Dao;
import androidx.room.Query;
import com.falcon.restaurants.room.BaseDao;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public abstract class RestaurantDao extends BaseDao<Restaurant> {

    @Query("SELECT * from Restaurant WHERE parentId=:parent_idV and active=1 ORDER BY name ASC")
    public abstract Observable<List<Restaurant>> getByParentId(String parent_idV);

    //check has children
    @Query("SELECT EXISTS(SELECT * FROM Restaurant WHERE parentId = :id)")
    public abstract Single<Boolean> hasChildren(String id);

    @Query("SELECT count(id) as count FROM Restaurant WHERE parentId = :id")
    public abstract Single<Integer> hasChildrenByCount(String id);

    //*** getMaxUpdated
    @Query("SELECT MAX(updatedAt) from Restaurant WHERE active=1")
    public abstract String getMaxUpdatedAt();

    //**** check exists
    @Query("SELECT EXISTS(SELECT * FROM Restaurant WHERE id = :id)")
    public abstract Single<Boolean> checkExists(String id);

    @Override
    public boolean checkExists(Restaurant restaurant){
        return checkExists(restaurant.getId()).blockingGet();
    }
}
