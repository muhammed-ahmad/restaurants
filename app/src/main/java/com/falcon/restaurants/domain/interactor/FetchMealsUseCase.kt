package com.falcon.restaurants.domain.interactor
import com.falcon.restaurants.data.network.meal.MealNet
import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.repository.MealRepository
import io.reactivex.Completable
import javax.inject.Inject
import io.reactivex.Observable
import io.reactivex.Single

class FetchMealsUseCase @Inject constructor (
    private val mealRepository : MealRepository
    ) {

    fun fetchAndUpsert(): Completable = mealRepository.fetchAndUpsert()

    fun getByRestaurantId(typeIdV: String): Observable<List<Meal>> = mealRepository.getByRestaurantId(typeIdV)

    fun getMealById(mealId: String): Single<Meal> = mealRepository.getMealById(mealId)

}
