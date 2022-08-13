package com.falcon.restaurants.data.net
import com.falcon.restaurants.data.net.model.RestaurantDto
import com.falcon.restaurants.data.net.model.MealDto
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("api/getRestaurants.php")
    fun  getRestaurantDtos(@Query("maxUpdatedAt") maxUpdatedAt: String ): Single<List<RestaurantDto>>

    @GET("api/getMeals.php")
    fun getMealDtos(@Query("maxUpdatedAt") maxUpdatedAt: String): Single<List<MealDto>>

    @FormUrlEncoded
    @POST("api/login.php")
    fun login(@Field("name") name: String, @Field("password") password: String): Single<List<MealDto>>

    @FormUrlEncoded
    @POST("api/register.php")
    fun register(@Field("name") name: String,
                 @Field("email") email: String,
                 @Field("password") password: String): Single<List<MealDto>>

}
