package com.falcon.restaurants.common.di.service
import android.app.Service
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ServiceModule (val service: Service){

    @Provides
    fun getContext(): Context = service
}
