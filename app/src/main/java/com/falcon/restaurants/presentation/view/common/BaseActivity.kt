package com.falcon.restaurants.presentation.view.common
import androidx.appcompat.app.AppCompatActivity
import com.falcon.restaurants.presentation.MyApplication
import com.falcon.restaurants.presentation.di.activity.ActivityModule
import com.falcon.restaurants.presentation.di.presentation.PresentationModule

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
