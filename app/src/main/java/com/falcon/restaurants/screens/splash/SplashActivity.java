package com.falcon.restaurants.screens.splash;

import android.os.Bundle;
import android.view.View;

import com.falcon.restaurants.databinding.ActivitySplashBinding;
import com.falcon.restaurants.screens.common.BaseActivity;
import com.falcon.restaurants.screens.common.ScreensNavigator;
import com.falcon.restaurants.utils.Logger;

import javax.inject.Inject;

import io.reactivex.plugins.RxJavaPlugins;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";
    private ActivitySplashBinding binding ;

    @Inject public SplashViewModel splashViewModel ;
    @Inject public ScreensNavigator screensNavigator ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getPresentationComponent().inject(this);
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        fetchAllData();
    }

    private void fetchAllData() {
        RxJavaPlugins.setErrorHandler(e -> { });
        splashViewModel.fetchAllData(new SplashViewModel.AllUpsertedListener() {
            @Override
            public void onSuccess() {
                Logger.log( TAG,"onSuccess: ");
                //binding.progressBar.setProgress(100);
                //binding.progressBar.setVisibility(View.INVISIBLE);
                screensNavigator.toCategoriesActivity("0");
            }

            @Override
            public void onFailed(Throwable e) {
                binding.progressBar.setVisibility(View.INVISIBLE);
                String error = e.getLocalizedMessage();
                Logger.log( TAG,"onFailed: " + error);
                //Toast.makeText(SplashActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                binding.errorTxt.setText(error);
            }
        });
    }
}