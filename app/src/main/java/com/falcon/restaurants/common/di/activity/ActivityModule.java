package com.falcon.restaurants.common.di.activity;

import android.content.Context;
import android.view.LayoutInflater;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    public FragmentActivity getFragmentActivity() {return activity;};

    @Provides
    public AppCompatActivity getActivity(){return activity;};

    @Provides
    public FragmentManager getFragmentManager(){return activity.getSupportFragmentManager();};

    @Provides
    public LayoutInflater getLayoutInflater() {return LayoutInflater.from(activity);}

    // FIXME this may lead to subtle errors
    @Provides
    Context getContext(){return activity;}

}
