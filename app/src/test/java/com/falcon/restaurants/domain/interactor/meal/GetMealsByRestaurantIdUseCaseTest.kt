package com.falcon.restaurants.domain.interactor.meal

import com.falcon.restaurants.domain.interactor.DomainTestData
import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.repository.MealRepository
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetMealsByRestaurantIdUseCaseTest {

    lateinit var SUT: GetMealsByRestaurantIdUseCase
    @Mock
    lateinit var mealRepository : MealRepository
    val MEALS_OF_TWO = mutableListOf(DomainTestData.createMeal1(), DomainTestData.createMeal3())
    val RESTAURANT2 = DomainTestData.createRestaurant2()

    @Before
    fun setUp(){
        SUT = GetMealsByRestaurantIdUseCase(mealRepository)
    }

    @Test
    fun execute_WhenRestaurantIdFound_ReturnsThisRestaurantMeals(){
        // arrange
        Mockito.`when`(mealRepository.getMealsByRestaurantId(RESTAURANT2.id)).thenReturn(Observable.just(MEALS_OF_TWO))
        // act
        val testObserver: TestObserver<List<Meal>> = SUT.execute(RESTAURANT2.id).test()
        // assert
        testObserver.assertComplete()
        testObserver.assertValue { meals -> meals[0].restaurantId.equals(RESTAURANT2.id) }
        testObserver.assertValue { meals -> meals.size == 2 }
        testObserver.assertValueCount(1)
    }

    @Test
    fun execute_WhenRestaurantIdNotFound_ReturnsEmptyList(){
        // arrange
        val notFoundId = "id1"
        val MEALS_EMPTY = mutableListOf<Meal>()
        Mockito.`when`(mealRepository.getMealsByRestaurantId(notFoundId)).thenReturn(Observable.just(MEALS_EMPTY))
        // act
        val testObserver: TestObserver<List<Meal>> = SUT.execute(notFoundId).test()
        // assert
        testObserver.assertComplete()
        testObserver.assertValue { meals -> meals.size == 0 }
    }

}