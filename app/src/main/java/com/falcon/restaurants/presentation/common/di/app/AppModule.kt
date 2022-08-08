package com.falcon.restaurants.presentation.common.di.app
import android.app.Application
import com.falcon.restaurants.data.network.RetrofitInterface
import com.falcon.restaurants.data.room.RoomDB
import com.falcon.restaurants.domain.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule (var application: Application){

    @Provides
    fun application() : Application = application

    @Provides
    fun getRoomDB(): RoomDB = RoomDB.getInstance(application)

    @AppScope
    @Provides
    fun getRetrofit(): Retrofit {

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val httpClient: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()

            val gson: Gson = GsonBuilder()
                    .setLenient()
                    .create()

            return  Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build()
    }

    @AppScope
    @Provides
    fun getRetrofitInterface(retrofit: Retrofit): RetrofitInterface = retrofit.create(RetrofitInterface::class.java)

}
