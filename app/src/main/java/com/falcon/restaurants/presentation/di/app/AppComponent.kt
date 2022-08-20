package com.falcon.restaurants.presentation.di.app
import com.falcon.restaurants.presentation.di.activity.ActivityComponent
import com.falcon.restaurants.presentation.di.activity.ActivityModule
import com.falcon.restaurants.presentation.di.service.ServiceComponent
import com.falcon.restaurants.presentation.di.service.ServiceModule
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun newActivityComponent(activityModule: ActivityModule): ActivityComponent
    fun newServiceComponent(serviceModule: ServiceModule): ServiceComponent
}
