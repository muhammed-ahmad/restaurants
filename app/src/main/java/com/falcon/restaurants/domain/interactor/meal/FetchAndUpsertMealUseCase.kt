package com.falcon.restaurants.domain.interactor.meal
import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.repository.MealRepository
import io.reactivex.Completable
import javax.inject.Inject
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class FetchAndUpsertMealUseCase @Inject constructor (
        private val fetchMealsUseCase: FetchMealsUseCase,
        private val upsertMealsUseCase: UpsertMealsUseCase
    ) {

    fun execute(): Completable {
        return Completable.defer {
            fetchMealsUseCase.execute().flatMapCompletable {
                    meals -> upsertMealsUseCase.execute(meals)
            }
        }
    }

}
