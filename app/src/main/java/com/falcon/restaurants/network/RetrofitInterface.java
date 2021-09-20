package com.falcon.restaurants.network;

import com.falcon.restaurants.network.restaurant.RestaurantNet;
import com.falcon.restaurants.network.meal.MealNet;
import java.util.List;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET("api/getRestaurants.php")
    public Single<List<RestaurantNet>> getRestaurants(@Query("maxUpdatedAt") String maxUpdatedAt);

    @GET("api/getMeals.php")
    public Single<List<MealNet>> getMeals(@Query("maxUpdatedAt") String maxUpdatedAt);

    @FormUrlEncoded
    @POST("cpanel/login.php")
    public Single<List<MealNet>> login(@Field("name") String name, @Field("password") String password);

    @FormUrlEncoded
    @POST("cpanel/register.php")
    public Single<List<MealNet>> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);

}
