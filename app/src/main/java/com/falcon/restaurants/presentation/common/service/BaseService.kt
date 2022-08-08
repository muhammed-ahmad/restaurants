package com.falcon.restaurants.presentation.common.service
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.falcon.restaurants.presentation.common.MyApplication
import com.falcon.restaurants.presentation.common.di.service.ServiceModule

class BaseService : Service() {

    val appComponent
        get() = (getApplication() as MyApplication).appComponent

    val serviceComponent by lazy {
        appComponent.newServiceComponent(ServiceModule(this))
    }

    // TODO to be changed
    override fun onBind(intent: Intent) : IBinder? {
        return null
    }
}
