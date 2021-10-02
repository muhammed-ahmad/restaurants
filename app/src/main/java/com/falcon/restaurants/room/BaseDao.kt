package com.falcon.restaurants.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Transaction
import androidx.room.Update
import com.falcon.restaurants.utils.Logger

@Dao
abstract class BaseDao<T>{

    val TAG: String = "BaseDao"

    @Insert
    abstract fun insert(obj: T): Long

    @Update
    abstract fun update(obj: T): Int

    @Delete
    abstract fun delete(obj: T)

    // ***************** getMaxUpdated *******************//

    abstract fun getMaxUpdatedAt(): String

    fun getMaxUpdated(): String{
        var maxUpdatedAt: String  = getMaxUpdatedAt()
        if( maxUpdatedAt == null || maxUpdatedAt.isEmpty()){
            maxUpdatedAt = "1970-01-01 00:00:01" 
        }
        return maxUpdatedAt
    }

    // ***************** upsert *******************//

    abstract fun checkExists(obj: T): Boolean

    @Transaction
    open fun upsert(obj: T): Long {
        var rowId: Long  = 0 
        val isExists: Boolean = checkExists(obj)
        if(!isExists) {
            rowId = insert(obj)
            Logger.log(TAG , " upsert: insert rowid: " + rowId)
        }else {
            rowId = update(obj).toLong()
            Logger.log(TAG , "upsert: update rowid: " + rowId)
        }
        return rowId 
    }

    @Transaction
    open fun upsert(objList: List<T>) {
        for (objectCurr in objList){
            upsert(objectCurr)
        }
    }
    // ******************************************//
}
