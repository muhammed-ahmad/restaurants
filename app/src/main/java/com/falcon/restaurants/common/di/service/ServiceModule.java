package com.falcon.restaurants.common.di.service;

import android.app.Service;
import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {
    Service service;

    public ServiceModule(Service service) {
        this.service = service;
    }

    @Provides
    Context getContext(){return service;}
}
