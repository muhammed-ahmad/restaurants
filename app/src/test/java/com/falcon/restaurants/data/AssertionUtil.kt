package com.falcon.restaurants.data

import com.falcon.restaurants.data.db.model.MealData
import com.falcon.restaurants.data.db.model.RestaurantData
import com.falcon.restaurants.data.net.model.MealDto
import com.falcon.restaurants.data.net.model.RestaurantDto
import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.model.Restaurant

object AssertionUtil {

    fun isMealMappedToMealData (meal: Meal, mealData: MealData): Boolean{
        return meal.id == mealData.id &&
                meal.name == mealData.name &&
                meal.details == mealData.details &&
                meal.imageUrl == mealData.imageUrl &&
                meal.restaurantId == mealData.restaurantId &&
                meal.updatedAt == mealData.updatedAt &&
                meal.active == mealData.active &&
                meal.favorite == mealData.favorite
    }

    fun isMealDataMappedToMeal (mealData: MealData, meal: Meal): Boolean{
        return isMealMappedToMealData (meal, mealData)
    }

    fun isMealDataListMappedToMealList (mealDatas: List<MealData>, meals: List<Meal>): Boolean{
        var isMapped = false
        for (i in mealDatas.indices){
            isMapped = isMealDataMappedToMeal(mealDatas[i], meals[i])
            if(!isMapped) break
        }
        return isMapped
    }

    fun isMealDtoMappedToMeal (mealDto: MealDto, meal: Meal): Boolean{
        return meal.id == mealDto.id &&
                meal.name == mealDto.name &&
                meal.details == mealDto.details &&
                meal.imageUrl == mealDto.imageUrl &&
                meal.restaurantId == mealDto.restaurantId &&
                meal.updatedAt == mealDto.updatedAt &&
                meal.active == mealDto.active &&
                meal.favorite == mealDto.favorite
    }

    fun isMealDtoListMappedToMealList (mealDtos: List<MealDto>, meals: List<Meal>): Boolean{
        var isMapped = false
        for (i in mealDtos.indices){
            isMapped = isMealDtoMappedToMeal(mealDtos[i], meals[i])
            if(!isMapped) break
        }
        return isMapped
    }


    fun isRestaurantDataMappedToRestaurant(restaurantData: RestaurantData, restaurant: Restaurant): Boolean {
        return restaurantData.id == restaurant.id &&
                restaurantData.parentId == restaurant.parentId &&
                restaurantData.name == restaurant.name &&
                restaurantData.imageUrl == restaurant.imageUrl &&
                restaurantData.active == restaurant.active &&
                restaurantData.updatedAt == restaurant.updatedAt
    }

    fun isRestaurantMappedToRestaurantData(restaurant: Restaurant, restaurantData: RestaurantData): Boolean {
        return isRestaurantDataMappedToRestaurant(restaurantData, restaurant)
    }

    fun isRestaurantDataListMappedToRestaurantList(restaurantDatas: MutableList<RestaurantData>, restaurants: List<Restaurant>): Boolean {
        var isMapped = false
        for (i in restaurantDatas.indices){
            isMapped = isRestaurantDataMappedToRestaurant(restaurantDatas[i], restaurants[i])
            if(!isMapped) break
        }
        return isMapped
    }

    fun isRestaurantDtoMappedToRestaurant(restaurantDto: RestaurantDto, restaurant: Restaurant): Boolean {
        return restaurantDto.id == restaurant.id &&
                restaurantDto.parentId == restaurant.parentId &&
                restaurantDto.name == restaurant.name &&
                restaurantDto.imageUrl == restaurant.imageUrl &&
                restaurantDto.active == restaurant.active &&
                restaurantDto.updatedAt == restaurant.updatedAt

    }

    fun isRestaurantDtoListMappedToRestaurantList(restaurantDtos: MutableList<RestaurantDto>, restaurants: List<Restaurant>): Boolean {
        var isMapped = false
        for (i in restaurantDtos.indices){
            isMapped = isRestaurantDtoMappedToRestaurant(restaurantDtos[i], restaurants[i])
            if(!isMapped) break
        }
        return isMapped
    }

}