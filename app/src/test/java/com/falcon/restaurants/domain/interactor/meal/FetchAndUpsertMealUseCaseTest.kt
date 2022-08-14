package com.falcon.restaurants.domain.interactor.meal

import com.falcon.restaurants.domain.interactor.DomainTestData
import com.falcon.restaurants.domain.model.Meal
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchAndUpsertMealUseCaseTest{

    lateinit var SUT: FetchAndUpsertMealUseCase
    @Mock lateinit var fetchMealsUseCase: FetchMealsUseCase
    @Mock lateinit var upsertMealsUseCase: UpsertMealsUseCase
    val MEALS: MutableList<Meal> = DomainTestData.createMeals()

    @Before
    fun setUp(){
        SUT = FetchAndUpsertMealUseCase(
            fetchMealsUseCase,
            upsertMealsUseCase
        )
    }

    @Test
    fun execute_fetchMealsSuccess_upsertMeals() {
        // Arrange
        Mockito.`when`(fetchMealsUseCase.execute()).thenReturn(Single.just(MEALS))
        Mockito.`when`(upsertMealsUseCase.execute(MEALS)).thenReturn(Completable.complete())
        // act
        val testObserver: TestObserver<Void> = SUT.execute().test()
        // assert
        testObserver.assertComplete()
        Mockito.verify(upsertMealsUseCase, Mockito.times(1)).execute(MEALS)
    }

    @Test
    fun execute_fetchMealsFailed_upsertMealsNotCalled() {
        // Arrange
        Mockito.`when`(fetchMealsUseCase.execute()).thenReturn(null)
        // act
        val testObserver: TestObserver<Void> = SUT.execute().test()
        // assert
        testObserver.assertNotComplete()
        testObserver.assertError(Throwable::class.java)
        Mockito.verify(upsertMealsUseCase, Mockito.never()).execute(ArrayList())
    }

}