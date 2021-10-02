package com.falcon.restaurants.common;

import androidx.multidex.MultiDexApplication;

import com.falcon.restaurants.common.di.app.AppComponent;
import com.falcon.restaurants.common.di.app.AppModule;
import com.falcon.restaurants.common.di.app.DaggerAppComponent

class MyApplication : MultiDexApplication() {

    val appComponent by lazy {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
    }
}
