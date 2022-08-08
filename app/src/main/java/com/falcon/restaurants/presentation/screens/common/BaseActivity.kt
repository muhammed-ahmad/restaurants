package com.falcon.restaurants.presentation.screens.common
import androidx.appcompat.app.AppCompatActivity
import com.falcon.restaurants.presentation.common.MyApplication
import com.falcon.restaurants.presentation.common.di.activity.ActivityModule
import com.falcon.restaurants.presentation.common.di.presentation.PresentationModule

open class BaseActivity : AppCompatActivity() {

    private val appComponent
        get() = (application as MyApplication).appComponent
    
    val activityComponent by lazy {
        appComponent.newActivityComponent(ActivityModule(this))
    }

    val presentationComponent by lazy {
        activityComponent.newPresentationComponent(PresentationModule())
    }

}
