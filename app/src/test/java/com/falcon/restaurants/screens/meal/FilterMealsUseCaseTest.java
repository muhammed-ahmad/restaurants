package com.falcon.restaurants.screens.meal;

import com.falcon.restaurants.room.meal.Meal;
import com.falcon.restaurants.testdata.MealsTestData;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class FilterMealsUseCaseTest {

    FilterMealsUseCase SUT ;

    public static final List<Meal> MAIN_MEALS = MealsTestData.getMainMeals();

    @Before
    public void setUp(){
        SUT = new FilterMealsUseCase();
        SUT.setList(MAIN_MEALS);
    }

    // filter with empty list then return empty list
    @Test
    public void filter_emptyList_returnEmptyList() {
        // arrange
        List<Meal> emptyMeals = new ArrayList<>();
        SUT = new FilterMealsUseCase();
        SUT.setList(emptyMeals);
        // act
        List<Meal> filteredMeals = SUT.filter("");
        // assert
        assertEquals(0, filteredMeals.size());
    }

    // filter with non empty list and the keyword is found then return a filtered list, the order is not conditional
    @Test
    public void filter_nonEmptyListAndQuery_returnFilteredList() {
        // act
        List<Meal> filteredMeals = SUT.filter("name");

        // assert (when search with "name" so it should gives 3 elements)
        assertThat(filteredMeals, Matchers.containsInAnyOrder(
                hasProperty("name", is("name1")),
                hasProperty("name", is("name3")),
                hasProperty("name", is("name2"))
        ));
    }

    // filter with non empty list and search with any keyword then search again with empty keyword then return the original list
    @Test
    public void filter_nonEmptyListAndQueryThenEmptyQuery_returnOriginalList() {

        // act1 (call with non empty keyword)
        List<Meal> filteredMeals = SUT.filter("name2");

        // assert1
        assertThat(filteredMeals, Matchers.containsInAnyOrder(
                hasProperty("name", is("name2"))
        ));

        // act2 (call with empty keyword)
        filteredMeals = SUT.filter("");

        // assert2 (it must be the original list)
        assertThat(filteredMeals, Matchers.containsInAnyOrder(
                hasProperty("name", is("name1")),
                hasProperty("name", is("name3")),
                hasProperty("name", is("name2"))
        ));
    }


    // filter with non empty list then with a non found keyword then return empty list
    @Test
    public void filter_nonEmptyListAndWithNonFoundQuery_returnEmptyList() {
        // act
        List<Meal> filteredMeals = SUT.filter("0000");

        // assert
        assertEquals(0, filteredMeals.size());
    }

}