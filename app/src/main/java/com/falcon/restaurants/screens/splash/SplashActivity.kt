package com.falcon.restaurants.screens.splash

import android.os.Bundle
import android.view.View

import com.falcon.restaurants.databinding.ActivitySplashBinding
import com.falcon.restaurants.screens.common.BaseActivity
import com.falcon.restaurants.screens.common.ScreensNavigator
import com.falcon.restaurants.utils.Logger

import javax.inject.Inject

import io.reactivex.plugins.RxJavaPlugins

class SplashActivity : BaseActivity() {

    val TAG: String = "SplashActivity"
    lateinit var binding: ActivitySplashBinding 

    @Inject lateinit var splashViewModel: SplashViewModel 
    @Inject lateinit var screensNavigator: ScreensNavigator 

    override fun onCreate(savedInstanceState: Bundle?) {
        presentationComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(getLayoutInflater())
        val view: View = binding.getRoot()
        setContentView(view)
        fetchAllData()
    }

    fun fetchAllData() {
        RxJavaPlugins.setErrorHandler{e -> { }}
        splashViewModel.fetchAllData(object : SplashViewModel.AllUpsertedListener {

            override fun onSuccess() {
                Logger.log( TAG,"onSuccess: ")
                //binding.progressBar.setProgress(100)
                //binding.progressBar.setVisibility(View.INVISIBLE)
                screensNavigator.toCategoriesActivity("0")
            }

            override fun onFailed(e: Throwable) {
                binding.progressBar.setVisibility(View.INVISIBLE)
                val error: String = e.getLocalizedMessage()
                Logger.log( TAG,"onFailed: " + error)
                //Toast.makeText(SplashActivity.this, "Error: " + error, Toast.LENGTH_LONG).show()
                binding.errorTxt.setText(error)
            }
        })
    }
}