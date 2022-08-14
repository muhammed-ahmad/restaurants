package com.falcon.restaurants.domain.interactor.meal
import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.repository.MealRepository
import io.reactivex.Completable
import javax.inject.Inject
import io.reactivex.Observable
import io.reactivex.Single

class FetchMealsUseCase @Inject constructor (
    private val mealRepository : MealRepository
    ) {

    fun execute(): Single<List<Meal>> = mealRepository.fetchMeals()

}
