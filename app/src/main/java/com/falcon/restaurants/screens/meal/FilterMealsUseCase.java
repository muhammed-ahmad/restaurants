package com.falcon.restaurants.screens.meal;

import com.falcon.restaurants.room.meal.Meal;
import com.falcon.restaurants.base.FilterInterface;
import javax.inject.Inject;

public class FilterMealsUseCase extends FilterInterface<Meal> {

    @Inject
    public FilterMealsUseCase() {}

    @Override
    public boolean isQueryFound(String query, Meal current) {

        return  current.getName().toLowerCase().contains(query.toLowerCase()) /* ||
                current.getDetails().toLowerCase().contains(query.toLowerCase())*/ ;
    }

}
