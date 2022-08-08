package com.falcon.restaurants.presentation.common.di.app
import com.falcon.restaurants.presentation.common.di.activity.ActivityComponent
import com.falcon.restaurants.presentation.common.di.activity.ActivityModule
import com.falcon.restaurants.presentation.common.di.service.ServiceComponent
import com.falcon.restaurants.presentation.common.di.service.ServiceModule
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun newActivityComponent(activityModule: ActivityModule): ActivityComponent
    fun newServiceComponent(serviceModule: ServiceModule): ServiceComponent
}
