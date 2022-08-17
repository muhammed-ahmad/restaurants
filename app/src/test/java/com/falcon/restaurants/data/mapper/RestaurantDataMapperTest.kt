package com.falcon.restaurants.data.mapper

import com.falcon.restaurants.data.AssertionUtil
import com.falcon.restaurants.data.DataTestData
import com.falcon.restaurants.data.db.model.RestaurantData
import com.falcon.restaurants.domain.model.Restaurant
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RestaurantDataMapperTest {

    lateinit var SUT: RestaurantDataMapper
    val restaurantDatas = DataTestData.createRestaurantDatas()
    val restaurantData1 = DataTestData.createRestaurantData1()
    val restaurantDtos = DataTestData.createRestaurantDtos()
    val restaurantDto1 = DataTestData.createRestaurantDto1()
    val restaurant1 = DataTestData.createRestaurant1()


    @Before
    fun setUp(){
        SUT = RestaurantDataMapper()
    }

    @Test
    fun dataToDomain_WithRestaurantData_ReturnMappedRestaurant(){
        // arrange
        // act
        val restaurant: Restaurant = SUT.dataToDomain(restaurantData1)
        // assert
        assertEquals(restaurant.id, restaurantData1.id)
        assertTrue(restaurant is Restaurant)
        assertTrue(AssertionUtil.isRestaurantDataMappedToRestaurant(restaurantData1, restaurant))
    }

    @Test
    fun dataToDomainList_WithRestaurantDataList_ReturnMappedRestaurantList(){
        // arrange
        // act
        val restaurants: List<Restaurant> = SUT.dataToDomainList(restaurantDatas)
        // assert
        assertEquals(restaurants.size, restaurantDatas.size)
        assertTrue(restaurants is List<Restaurant>)
        assertTrue(AssertionUtil.isRestaurantDataListMappedToRestaurantList(restaurantDatas, restaurants))
    }

    @Test
    fun domainToData_WithRestaurant_ReturnMappedRestaurantData(){
        // arrange
        // act
        val restaurantData: RestaurantData = SUT.domainToData(restaurant1)
        // assert
        assertEquals(restaurantData.id, restaurant1.id)
        assertTrue(restaurantData is RestaurantData)
        assertTrue(AssertionUtil.isRestaurantMappedToRestaurantData(restaurant1, restaurantData))
    }

    @Test
    fun dtoToDomain_WithRestaurantDto_ReturnMappedRestaurant(){
        // arrange
        // act
        val restaurant: Restaurant = SUT.dtoToDomain(restaurantDto1)
        // assert
        assertEquals(restaurant.id, restaurantDto1.id)
        assertTrue(restaurant is Restaurant)
        assertTrue(AssertionUtil.isRestaurantDtoMappedToRestaurant(restaurantDto1, restaurant))
    }

    @Test
    fun dtoToDomainList_WithRestaurantDtoList_ReturnMappedRestaurantList(){
        // arrange
        // act
        val restaurants: List<Restaurant> = SUT.dtoToDomainList(restaurantDtos)
        // assert
        assertEquals(restaurants.size, restaurantDtos.size)
        assertTrue(restaurants is List<Restaurant>)
        assertTrue(AssertionUtil.isRestaurantDtoListMappedToRestaurantList(restaurantDtos, restaurants))
    }


}