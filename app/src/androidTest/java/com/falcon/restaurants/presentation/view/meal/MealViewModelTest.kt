package com.falcon.restaurants.presentation.view.meal

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.falcon.restaurants.domain.interactor.meal.FetchAndUpsertMealUseCase
import com.falcon.restaurants.domain.interactor.meal.GetMealByIdUseCase
import com.falcon.restaurants.domain.interactor.meal.GetMealsByRestaurantIdUseCase
import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.presentation.PresentationAndroidTestData
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import java.util.concurrent.Callable

@RunWith(MockitoJUnitRunner::class)
class MealViewModelTest  {

    lateinit var SUT: MealViewModel
    @Mock lateinit var application: Application
    @Mock lateinit var fetchAndUpsertMealUseCase: FetchAndUpsertMealUseCase
    @Mock lateinit var getMealsByRestaurantIdUseCase: GetMealsByRestaurantIdUseCase
    @Mock lateinit var getMealByIdUseCase: GetMealByIdUseCase

    @Mock lateinit var lifecycleObserver: Observer<List<Meal>>
    @Captor lateinit var captor: ArgumentCaptor<List<Meal>>

    @Mock lateinit var getMealByIdListener: MealViewModel.GetMealByIdListener

    val meals = PresentationAndroidTestData.createMeals()

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { schedulerCallable: Callable<Scheduler?>? -> Schedulers.trampoline() }
        SUT = MealViewModel(application, fetchAndUpsertMealUseCase, getMealsByRestaurantIdUseCase, getMealByIdUseCase)
    }

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getMealsByRestaurantId_WhenNonEmptyData_ReturnLiveDataOfMealsList(){
        // arrange
        val restaurantId = "id2"
        Mockito.`when`(getMealsByRestaurantIdUseCase.execute(restaurantId)).thenReturn(Observable.just(meals))
        // act
        SUT.getMealsByRestaurantId(restaurantId).observeForever(lifecycleObserver)
        // assert
        captor.run {
            Mockito.verify(lifecycleObserver, Mockito.times(1)).onChanged(capture())
            assertEquals(meals, value)
        }
    }

    @Test
    fun getMealsByRestaurantId_WhenEmptyData_ThenLiveDataNotObservedOfEmptyMealsList(){
        // arrange
        val restaurantId = ""
        Mockito.`when`(getMealsByRestaurantIdUseCase.execute(restaurantId)).thenReturn(Observable.empty())
        // act
        SUT.getMealsByRestaurantId(restaurantId).observeForever(lifecycleObserver)
        // assert
        Mockito.verify(lifecycleObserver, never()).onChanged(any())
    }

    @Test
    fun getMealById_WhenSuccess_ThenCallGetMealByIdListenerOnSuccess(){
        // arrange
        val mealId = ""
        val meal1 = PresentationAndroidTestData.createMeal1()
        Mockito.`when`(getMealByIdUseCase.execute(mealId)).thenReturn(Single.just(meal1))
        // act
        SUT.getMealById(mealId, getMealByIdListener)
        // success
        Mockito.verify(getMealByIdListener).onSuccess(any())
        Mockito.verify(getMealByIdListener, never()).onFailed(any())
    }

    @Test
    fun getMealById_WhenFail_ThenCallGetMealByIdListenerOnFailed(){
        // arrange
        val mealId = ""
        val throwable = Throwable("Meal Not Found")
        Mockito.`when`(getMealByIdUseCase.execute(mealId)).thenReturn(Single.error(throwable))
        // act
        SUT.getMealById(mealId, getMealByIdListener)
        // assert
        Mockito.verify(getMealByIdListener).onFailed(any())
        Mockito.verify(getMealByIdListener, never()).onSuccess(any())
    }

}