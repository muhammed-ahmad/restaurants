package com.falcon.restaurants.domain.repository
import com.falcon.restaurants.domain.model.Meal
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface MealRepository {

    fun fetchAndUpsert(): Completable

    fun getMealsByRestaurantId(typeIdV: String): Observable<List<Meal>>

    fun getMealById(mealId: String): Single<Meal>

    fun upsert(meal: Meal)

    fun upsert(meals: List<Meal>): Completable
}
