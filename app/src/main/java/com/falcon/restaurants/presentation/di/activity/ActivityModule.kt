package com.falcon.restaurants.presentation.di.activity
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides

@Module
class ActivityModule (val activity: AppCompatActivity){

    @Provides
    fun getFragmentActivity(): FragmentActivity = activity

    @Provides
    fun activity(): AppCompatActivity = activity

    @Provides
    fun getFragmentManager(): FragmentManager = activity.getSupportFragmentManager()

    @Provides
    fun getLayoutInflater(): LayoutInflater = LayoutInflater.from(activity)

    // FIXME this may lead to subtle errors
    @Provides
    fun getContext(): Context = activity

}
