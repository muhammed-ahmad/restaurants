package com.falcon.restaurants.presentation.common.di.service
import android.app.Service
import dagger.Subcomponent

@Subcomponent(modules = [ServiceModule::class])
interface ServiceComponent {
    // FIXME this will be a concrete service
    fun inject(service: Service)
}
