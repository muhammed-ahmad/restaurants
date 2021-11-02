package com.falcon.restaurants.screens.meal
import com.falcon.restaurants.room.meal.Meal
import com.falcon.restaurants.base.FilterBase
import javax.inject.Inject

class FilterMealsUseCase @Inject constructor(): FilterBase<Meal>() {

    override fun isQueryFound(query: String , current: Meal): Boolean{

        return current.name.lowercase().contains(query.lowercase()) /* ||
                current.details.lowercase().contains(query.lowercase())*/
    }

}
