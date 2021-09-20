package com.falcon.restaurants.common.di.service;

import android.app.Service;

import dagger.Subcomponent;

@Subcomponent(modules = {ServiceModule.class})
public interface ServiceComponent {
    // TODO this will be a concrete service
    void inject(Service service);
}
