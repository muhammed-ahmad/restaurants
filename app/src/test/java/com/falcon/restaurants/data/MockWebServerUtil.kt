package com.falcon.restaurants.data

import com.falcon.restaurants.data.net.RetrofitInterface
import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.model.Restaurant
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MockWebServerUtil {

    fun setMockWebServerResponse(mockWebServer: MockWebServer, jsonResponse: String) {
        val response = MockResponse()
            .setBody(jsonResponse)
            .setResponseCode(200)
        mockWebServer.enqueue(response)
    }

    fun getRetrofitInterfaceMock(mockWebServer: MockWebServer): RetrofitInterface {

        val httpClient: OkHttpClient = OkHttpClient.Builder().build()

        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
            .create(RetrofitInterface::class.java)
    }

    // ************************** test data for MockWebServer

    // ********* restaurants
    fun createRestaurant1() = Restaurant("id1", "0", "name1","image_url", "1", "updated_at")
    fun createRestaurant2() = Restaurant("id2", "0", "name2","image_url", "1", "updated_at")
    fun createRestaurant3() = Restaurant("id3", "0", "name3","image_url", "1", "updated_at")

    fun createRestaurants(): MutableList<Restaurant> = mutableListOf(
        createRestaurant1(), createRestaurant2(), createRestaurant3()
    )

    fun getEmptyRestaurantsResponse() = """[]"""
    fun getSuccessRestaurantsResponse() = """
    [
        {"name":"name1","imageUrl":"image_url","active":1,"id":id1,"updatedAt":"updated_at","parentId":0},
        {"name":"name2","imageUrl":"image_url","active":1,"id":id2,"updatedAt":"updated_at","parentId":0},
        {"name":"name3","imageUrl":"image_url","active":1,"id":id3,"updatedAt":"updated_at","parentId":0}
    ]
    """.trimIndent()

    fun getErrorRestaurantsResponse() = ""

    // ********* Meals
    fun createMeal1() = Meal("id1", "name1", "detail1", "image_url", "id2",
        "updated_at", "1", "0")
    fun createMeal2() = Meal("id2", "name2", "detail2", "image_url", "id1",
        "updated_at", "1", "0")
    fun createMeal3() = Meal("id3", "name3", "detail3", "image_url", "id2",
        "updated_at", "1", "0")

    fun createMeals(): MutableList<Meal> = mutableListOf(
        createMeal1(), createMeal2(), createMeal3()
    )

    fun getSuccessMealsResponse() = """
    [
        {"id":"id1","name":"name1","details":"detail1","imageUrl":"image_url","restaurantId":"id2","updatedAt":"updated_at","active":1,"favorite":0},
        {"id":"id2","name":"name2","details":"detail2","imageUrl":"image_url","restaurantId":"id1","updatedAt":"updated_at","active":1,"favorite":0},
        {"id":"id3","name":"name3","details":"detail3","imageUrl":"image_url","restaurantId":"id2","updatedAt":"updated_at","active":1,"favorite":0}
    ]  
    """.trimIndent()

    fun getEmptyMealsResponse() = """[]"""


}