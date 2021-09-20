package com.falcon.restaurants.screens.splash;

import android.annotation.SuppressLint;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.falcon.restaurants.screens.restaurant.RestaurantViewModel;
import com.falcon.restaurants.screens.meal.MealViewModel;
import com.falcon.restaurants.utils.Logger;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class SplashViewModel extends AndroidViewModel {

    private static final String TAG = "SplashViewModel";

    RestaurantViewModel restaurantViewModel;
    MealViewModel mealViewModel;

    public SplashViewModel(@NonNull Application application,
                           RestaurantViewModel restaurantViewModel,
                           MealViewModel mealViewModel
                           ) {
        super(application);
        this.restaurantViewModel = restaurantViewModel;
        this.mealViewModel = mealViewModel;
    }

    public interface AllUpsertedListener{
        void onSuccess();
        void onFailed(Throwable e);
    }

    @SuppressLint("CheckResult")
    public void fetchAllData(AllUpsertedListener allUpsertedListener){

       Observer<String> observer = new Observer<String>() {
           @Override
           public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
               Logger.log( TAG, "onSubscribe: ");
           }

           @Override
           public void onNext(@io.reactivex.annotations.NonNull String string) {
               Logger.log( TAG, "onNext: " + string);
           }

           @Override
           public void onError(@io.reactivex.annotations.NonNull Throwable e) {
               Logger.log( TAG, "onError: " + e.getLocalizedMessage());
               allUpsertedListener.onFailed(e);
           }

           @Override
           public void onComplete() {
               Logger.log(TAG,  "onComplete: ");
               allUpsertedListener.onSuccess();
           }
       };

        Logger.log( TAG, "startDownload: ");

        Observable.zip(
                mealViewModel.fetch(),
                restaurantViewModel.fetch(),
                new BiFunction<String, String, String>() {
                    @io.reactivex.annotations.NonNull
                    @Override
                    public String apply(@io.reactivex.annotations.NonNull String s,
                                        @io.reactivex.annotations.NonNull String s2) throws Exception {
                        Logger.log( TAG, "apply: s: " + s + " , s2: " + s2);
                        return s + " " + s2;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()).subscribe(observer);
    }

}
