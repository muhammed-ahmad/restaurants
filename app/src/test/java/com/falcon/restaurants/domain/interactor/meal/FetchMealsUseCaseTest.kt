package com.falcon.restaurants.domain.interactor.meal

import com.falcon.restaurants.domain.interactor.DomainTestData
import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.repository.MealRepository
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchMealsUseCaseTest{

    lateinit var SUT: FetchMealsUseCase
    @Mock lateinit var mealRepository: MealRepository
    val MEALS: MutableList<Meal> = DomainTestData.createMeals()

    @Before
    fun setUp(){
        SUT = FetchMealsUseCase(mealRepository)
    }

    @Test
    fun execute_withNonEmptyListInRepo_returnNonEmptyList(){
        // arrange
        `when`(mealRepository.fetchMeals()).thenReturn(Single.just(MEALS))
        // act
        val testObserver: TestObserver<List<Meal>> = SUT.execute().test()
        // assert
        testObserver.assertComplete()
        testObserver.assertValue(MEALS)
    }

    @Test
    fun execute_withEmptyListInRepo_returnEmptyList(){
        // arrange
        val meals: MutableList<Meal> = mutableListOf()
        `when`(mealRepository.fetchMeals()).thenReturn(Single.just(meals))
        // act
        val testObserver: TestObserver<List<Meal>> = SUT.execute().test()
        // assert
        testObserver.assertComplete()
        testObserver.assertValue(meals)
    }

}