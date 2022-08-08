package com.falcon.restaurants.domain.repository
import com.falcon.restaurants.data.network.meal.MealNet
import com.falcon.restaurants.data.room.meal.MealModel
import com.falcon.restaurants.domain.model.Meal
import io.reactivex.Observable
import io.reactivex.Single

interface MealRepository {

    fun fetch(): Observable<String>

    fun getByRestaurantId(typeIdV: String): Observable<List<Meal>>

    fun getMealById(mealId: String): Single<Meal>

    fun upsert(mealNet: MealNet)

    fun upsert(mealNets: List<MealNet>)
}
