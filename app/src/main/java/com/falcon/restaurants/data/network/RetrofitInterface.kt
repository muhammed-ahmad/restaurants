package com.falcon.restaurants.data.network
import com.falcon.restaurants.data.network.restaurant.RestaurantNet
import com.falcon.restaurants.data.network.meal.MealNet
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("api/getRestaurants.php")
    fun  getRestaurants(@Query("maxUpdatedAt") maxUpdatedAt: String ): Single<List<RestaurantNet>>

    @GET("api/getMeals.php")
    fun getMeals(@Query("maxUpdatedAt") maxUpdatedAt: String): Single<List<MealNet>>

    @FormUrlEncoded
    @POST("api/login.php")
    fun login(@Field("name") name: String, @Field("password") password: String): Single<List<MealNet>>

    @FormUrlEncoded
    @POST("api/register.php")
    fun register(@Field("name") name: String,
                 @Field("email") email: String,
                 @Field("password") password: String): Single<List<MealNet>> 

}
