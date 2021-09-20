package com.falcon.restaurants.room.meal;

import androidx.room.Dao;
import androidx.room.Query;
import com.falcon.restaurants.room.BaseDao;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public abstract class MealDao extends BaseDao<Meal> {

    private static final String TAG = "MealDao";

    @Query("SELECT * from Meal WHERE restaurantId=:typeIdV ORDER BY updatedAt ASC")
    public abstract Observable<List<Meal>> getByRestaurantId(String typeIdV);

    @Query("SELECT * from Meal WHERE id=:mealIdV")
    public abstract Single<Meal> getMealById(String mealIdV);

    //*** getMaxUpdated
    @Query("SELECT MAX(updatedAt) from Meal WHERE active=1")
    public abstract String getMaxUpdatedAt();

    //**** check exists
    @Query("SELECT EXISTS(SELECT * FROM Meal WHERE id = :idV)")
    public abstract Single<Boolean> checkExists(String idV);

    @Override
    public boolean checkExists(Meal meal){
        return checkExists(meal.getId()).blockingGet();
    }

}
