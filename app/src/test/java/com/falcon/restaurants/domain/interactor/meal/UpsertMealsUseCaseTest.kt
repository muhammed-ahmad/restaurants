package com.falcon.restaurants.domain.interactor.meal

import com.falcon.restaurants.domain.repository.MealRepository
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock

class UpsertMealsUseCaseTest{

    lateinit var SUT: UpsertMealsUseCase
    @Mock lateinit var mealRepository: MealRepository

    @Before
    fun setUp(){
        SUT = UpsertMealsUseCase(mealRepository)
    }



}