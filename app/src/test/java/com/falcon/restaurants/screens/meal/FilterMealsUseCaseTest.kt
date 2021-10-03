package com.falcon.restaurants.screens.meal

import com.falcon.restaurants.room.meal.Meal
import com.falcon.restaurants.testdata.MealsTestData
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import java.util.ArrayList
import org.hamcrest.Matchers.hasProperty
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*

class FilterMealsUseCaseTest {

    lateinit var SUT: FilterMealsUseCase 

    val MAIN_MEALS: MutableList<Meal> = MealsTestData.getMainMeals()

    @Before
    fun setUp(){
        SUT = FilterMealsUseCase()
        SUT.setList(MAIN_MEALS)
    }

    // filter with empty list then return empty list
    @Test
    fun filter_emptyList_returnEmptyList() {
        // arrange
        val emptyMeals: MutableList<Meal> = ArrayList()
        SUT = FilterMealsUseCase()
        SUT.setList(emptyMeals)
        // act
        val filteredMeals: MutableList<Meal> = SUT.filter("")
        // assert
        assertEquals(0, filteredMeals.size)
    }

    // filter with non empty list and the keyword is found then return a filtered list, the order is not conditional
    @Test
    fun filter_nonEmptyListAndQuery_returnFilteredList() {
        // act
        val filteredMeals: MutableList<Meal> = SUT.filter("name")

        // assert (when search with "name" so it should gives 3 elements)
        assertThat(filteredMeals, Matchers.containsInAnyOrder(
                hasProperty("name", `is`("name1")),
                hasProperty("name", `is`("name3")),
                hasProperty("name", `is`("name2"))
        ))
    }

    // filter with non empty list and search with any keyword then search again with empty keyword then return the original list
    @Test
    fun filter_nonEmptyListAndQueryThenEmptyQuery_returnOriginalList() {

        // act1 (call with non empty keyword)
        var filteredMeals: MutableList<Meal> = SUT.filter("name2")

        // assert1
        assertThat(filteredMeals, Matchers.containsInAnyOrder(
                hasProperty("name", `is`("name2"))
        ))

        // act2 (call with empty keyword)
        filteredMeals = SUT.filter("")

        // assert2 (it must be the original list)
        assertThat(filteredMeals, Matchers.containsInAnyOrder(
                hasProperty("name", `is`("name1")),
                hasProperty("name", `is`("name3")),
                hasProperty("name", `is`("name2"))
        ))
    }


    // filter with non empty list then with a non found keyword then return empty list
    @Test
    fun filter_nonEmptyListAndWithNonFoundQuery_returnEmptyList() {
        // act
        val filteredMeals: MutableList<Meal> = SUT.filter("0000")
        // assert
        assertEquals(0, filteredMeals.size)
    }

}