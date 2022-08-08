package com.falcon.restaurants.domain.interactor
import com.falcon.restaurants.data.network.meal.MealNet
import com.falcon.restaurants.data.room.meal.MealModel
import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.repository.MealRepository
import javax.inject.Inject
import io.reactivex.Observable
import io.reactivex.Single

class FetchMealsUseCase @Inject constructor (
    private val mealRepository : MealRepository
    ) {

    fun fetch(): Observable<String> = mealRepository.fetch()

    fun getByRestaurantId(typeIdV: String): Observable<List<Meal>> = mealRepository.getByRestaurantId(typeIdV)

    fun getMealById(mealId: String): Single<Meal> = mealRepository.getMealById(mealId)

    fun upsert(mealNet: MealNet) = mealRepository.upsert(mealNet)

    fun upsert(mealNets: List<MealNet>) = mealRepository.upsert(mealNets)
}
