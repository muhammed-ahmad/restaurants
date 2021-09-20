package com.falcon.restaurants.screens.restaurant;

import com.falcon.restaurants.base.FilterInterface;
import com.falcon.restaurants.room.restaurant.Restaurant;

import javax.inject.Inject;

public class FilterRestaurantsUseCase extends FilterInterface<Restaurant> {

    @Inject
    public FilterRestaurantsUseCase() {}

    @Override
    public boolean isQueryFound(String query, Restaurant current) {
        return current.getName().toLowerCase().contains(query.toLowerCase());
    }
}
