package com.falcon.restaurants.data.mapper

import com.falcon.restaurants.data.AssertionUtil
import com.falcon.restaurants.data.DataTestData
import com.falcon.restaurants.data.db.model.MealData
import com.falcon.restaurants.domain.model.Meal
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MealDataMapperTest {

    lateinit var SUT: MealDataMapper
    val mealDatas = DataTestData.createMealDatas()
    val mealData1 = DataTestData.createMealData1()
    val mealDtos = DataTestData.createMealDtos()
    val mealDto1 = DataTestData.createMealDto1()
    val meal1 = DataTestData.createMeal1()


    @Before
    fun setUp(){
        SUT = MealDataMapper()
    }

    @Test
    fun dataToDomain_WithMealData_ReturnsMappedMeal(){
        // arrange
        // act
        val meal: Meal = SUT.dataToDomain(mealData1)
        // assert
        assertEquals(meal.id, mealData1.id)
        assertTrue(meal is Meal)
        assertTrue(AssertionUtil.isMealDataMappedToMeal(mealData1, meal))
    }

    @Test
    fun dataToDomainList_WithMealDataList_ReturnsMappedMealList(){
        // arrange
        // act
        val meals: List<Meal> = SUT.dataToDomainList(mealDatas)
        // assert
        assertEquals(meals.size, mealDatas.size)
        assertTrue(meals is List<Meal>)
        assertTrue(AssertionUtil.isMealDataListMappedToMealList(mealDatas, meals))
    }

    @Test
    fun domainToData_WithMeal_ReturnsMappedMealData(){
        // arrange
        // act
        val mealData: MealData = SUT.domainToData(meal1)
        // assert
        assertEquals(mealData.id, meal1.id)
        assertTrue(mealData is MealData)
        assertTrue(AssertionUtil.isMealMappedToMealData(meal1, mealData))
    }

    @Test
    fun dtoToDomain_WithMealDto_ReturnsMappedMeal(){
        // arrange
        // act
        val meal: Meal = SUT.dtoToDomain(mealDto1)
        // assert
        assertEquals(meal.id, mealDto1.id)
        assertTrue(meal is Meal)
        assertTrue(AssertionUtil.isMealDtoMappedToMeal(mealDto1, meal))
    }

    @Test
    fun dtoToDomainList_WithMealDtoList_ReturnsMappedMealList(){
        // arrange
        // act
        val meals: List<Meal> = SUT.dtoToDomainList(mealDtos)
        // assert
        assertEquals(meals.size, mealDtos.size)
        assertTrue(meals is List<Meal>)
        assertTrue(AssertionUtil.isMealDtoListMappedToMealList(mealDtos, meals))
    }


}