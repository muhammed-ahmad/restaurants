package com.falcon.restaurants.common.di.app;

import android.app.Application;
import com.falcon.restaurants.network.RetrofitInterface;
import com.falcon.restaurants.room.RoomDB;
import com.falcon.restaurants.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    public Application application ;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    public Application getApplication() { return application; }

    @Provides
    public RoomDB getRoomDB() {
        return RoomDB.getInstance(application);
    }

    @AppScope
    @Provides
    public Retrofit getRetrofit() {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            return new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build();
    }

    @AppScope
    @Provides
    public RetrofitInterface getRetrofitInterface(Retrofit retrofit) {
        return retrofit.create(RetrofitInterface .class);
    }

}
