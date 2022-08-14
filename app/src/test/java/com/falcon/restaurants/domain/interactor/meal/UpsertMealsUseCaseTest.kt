package com.falcon.restaurants.domain.interactor.meal

import com.falcon.restaurants.domain.interactor.DomainTestData
import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.repository.MealRepository
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UpsertMealsUseCaseTest{

    lateinit var SUT: UpsertMealsUseCase
    @Mock lateinit var mealRepository: MealRepository
    val MEALS: MutableList<Meal> = DomainTestData.createMeals()

    @Before
    fun setUp(){
        SUT = UpsertMealsUseCase(mealRepository)
    }

    @Test
    fun execute_upsertListOfMeal_returnCompletable(){
        // arrange
        Mockito.`when`(mealRepository.upsert(MEALS)).thenReturn(Completable.complete())
        // act
        val testObserver = SUT.execute(MEALS).test()
        // assert
        testObserver.assertComplete()
        Mockito.verify(mealRepository, Mockito.times(1)).upsert(MEALS)
    }

}