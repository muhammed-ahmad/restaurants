package com.falcon.restaurants.screens.common;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.falcon.restaurants.common.MyApplication;
import com.falcon.restaurants.common.di.app.AppComponent;
import com.falcon.restaurants.common.di.service.ServiceComponent;
import com.falcon.restaurants.common.di.service.ServiceModule;

public class BaseService extends Service {

    private AppComponent getAppComponent() {
        return ((MyApplication)getApplication()).getAppComponent();
    }

    private ServiceComponent serviceComponent;
    public ServiceComponent getServiceComponent() {
        if(serviceComponent == null) {
            serviceComponent = getAppComponent().newServiceComponent(new ServiceModule(this));
        }
        return serviceComponent;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
