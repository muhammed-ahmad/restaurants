package com.falcon.restaurants.domain.interactor.meal

import com.falcon.restaurants.domain.interactor.DomainTestData
import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.repository.MealRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetMealByIdUseCaseTest {

    lateinit var SUT: GetMealByIdUseCase
    @Mock lateinit var mealRepository : MealRepository
    val MEAL2 = DomainTestData.createMeal2()

    @Before
    fun setUp(){
        SUT = GetMealByIdUseCase(mealRepository)
    }

    @Test
    fun execute_WhenMealIdFound_ReturnsMealById(){
        // arrange
        Mockito.`when`(mealRepository.getMealById(MEAL2.id)).thenReturn(Single.just(MEAL2))
        // act
        val testObserver: TestObserver<Meal> = SUT.execute(MEAL2.id).test()
        // assert
        testObserver.assertComplete()
        testObserver.assertValue { meal -> meal.id.equals("id2") }
        testObserver.assertValueCount(1)
    }

    @Test
    fun execute_WhenMealIdNotFound_ReturnsError(){
        // arrange
        val notFoundId = "id1"
        val throwable = Throwable("Item Not Found")
        Mockito.`when`(mealRepository.getMealById(notFoundId)).thenReturn(Single.error(throwable))
        // act
        val testObserver: TestObserver<Meal> = SUT.execute(notFoundId).test()
        // assert
        testObserver.assertNotComplete()
        testObserver.assertError(throwable)
    }

}