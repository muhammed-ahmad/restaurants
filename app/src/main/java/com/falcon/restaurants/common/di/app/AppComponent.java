package com.falcon.restaurants.common.di.app;

import com.falcon.restaurants.common.di.activity.ActivityComponent;
import com.falcon.restaurants.common.di.activity.ActivityModule;
import com.falcon.restaurants.common.di.service.ServiceComponent;
import com.falcon.restaurants.common.di.service.ServiceModule;
import dagger.Component;

@AppScope
@Component(modules = {AppModule.class})
public interface AppComponent {

    ActivityComponent newActivityComponent(ActivityModule activityModule);
    ServiceComponent newServiceComponent(ServiceModule serviceModule);
}
