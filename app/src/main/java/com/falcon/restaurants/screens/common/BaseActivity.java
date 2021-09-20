package com.falcon.restaurants.screens.common;

import androidx.appcompat.app.AppCompatActivity;
import com.falcon.restaurants.common.MyApplication;
import com.falcon.restaurants.common.di.activity.ActivityComponent;
import com.falcon.restaurants.common.di.activity.ActivityModule;
import com.falcon.restaurants.common.di.app.AppComponent;
import com.falcon.restaurants.common.di.presentation.PresentationComponent;
import com.falcon.restaurants.common.di.presentation.PresentationModule;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    private AppComponent getAppComponent() {
        return ((MyApplication)getApplication()).getAppComponent();
    }

    private ActivityComponent activityComponent;
    public ActivityComponent getActivityComponent() {
        if(activityComponent == null) {
            activityComponent = getAppComponent().newActivityComponent(new ActivityModule(this));
        }
        return activityComponent;
    }

    PresentationComponent presentationComponent;
    public PresentationComponent getPresentationComponent() {
        if(presentationComponent == null) {
            presentationComponent = getActivityComponent().newPresentationComponent(new PresentationModule());
        }
        return presentationComponent;
    }

}
