package com.falcon.restaurants.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Transaction;
import androidx.room.Update;
import java.util.List;
import java.util.concurrent.Callable;
import io.reactivex.Single;

@Dao
public abstract class BaseDao<T>{

    private static final String TAG = "BaseDao";

    @Insert
    public abstract long insert(T obj);

    @Update
    public abstract int update(T obj);

    @Delete
    public abstract void delete(T obj);

    // ***************** getMaxUpdated *******************//

    public abstract String getMaxUpdatedAt();

    public String getMaxUpdated(){
        //return Single.fromCallable(new Callable<String>() {
        //    @Override
        //    public String call() throws Exception {
                String maxUpdatedAt = getMaxUpdatedAt();
                if( maxUpdatedAt == null || maxUpdatedAt.isEmpty()){
                    maxUpdatedAt = "1970-01-01 00:00:01" ;
                }
                return maxUpdatedAt;
         //   }
        //});
    }

    // ***************** upsert *******************//

    public abstract boolean checkExists(T obj);

    @Transaction
    public long upsert(T obj) {
        long rowId = 0 ;
        boolean isExists = checkExists(obj);
        if(!isExists) {
            rowId = insert(obj);
            System.out.println(TAG + " upsert: insert rowid: " + rowId);
        }else {
            rowId = update(obj);
            System.out.println(TAG + "upsert: update rowid: " + rowId);
        }
        return rowId ;
    }

    @Transaction
    public void upsert(List<T> objList) {
        for (int i = 0; i < objList.size(); i++) {
            upsert(objList.get(i));
        }
    }
    // ******************************************//
}
