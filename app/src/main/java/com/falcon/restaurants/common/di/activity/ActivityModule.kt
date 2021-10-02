package com.falcon.restaurants.common.di.activity

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
    fun getFragmentActivity(): FragmentActivity {return activity}

    @Provides
    fun activity(): AppCompatActivity {return activity}

    @Provides
    fun getFragmentManager(): FragmentManager {return activity.getSupportFragmentManager()}

    @Provides
    fun getLayoutInflater(): LayoutInflater {return LayoutInflater.from(activity)}

    // FIXME this may lead to subtle errors
    @Provides
    fun getContext(): Context {return activity}

}
