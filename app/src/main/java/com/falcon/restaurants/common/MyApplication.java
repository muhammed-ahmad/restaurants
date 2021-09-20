package com.falcon.restaurants.common;

import androidx.multidex.MultiDexApplication;

import com.falcon.restaurants.common.di.app.AppComponent;
import com.falcon.restaurants.common.di.app.AppModule;
import com.falcon.restaurants.common.di.app.DaggerAppComponent;

public class MyApplication extends MultiDexApplication {

    private AppComponent appComponent;
    public synchronized AppComponent getAppComponent() {
        if(appComponent == null){
            appComponent = DaggerAppComponent
                    .builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
