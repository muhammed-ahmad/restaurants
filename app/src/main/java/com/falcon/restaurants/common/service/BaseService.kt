package com.falcon.restaurants.common.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.falcon.restaurants.common.MyApplication;
import com.falcon.restaurants.common.di.app.AppComponent;
import com.falcon.restaurants.common.di.service.ServiceComponent;
import com.falcon.restaurants.common.di.service.ServiceModule;

class BaseService : Service() {

    val appComponent get() = (getApplication() as MyApplication).appComponent

    val serviceComponent by lazy {
        appComponent.newServiceComponent(ServiceModule(this))
    }

    // TODO to be changed
    override fun onBind(intent: Intent) : IBinder? {
        return null
    }
}
