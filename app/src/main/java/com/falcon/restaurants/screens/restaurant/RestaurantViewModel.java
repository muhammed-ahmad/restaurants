package com.falcon.restaurants.screens.restaurant;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.falcon.restaurants.base.LocalListeners;
import com.falcon.restaurants.room.restaurant.Restaurant;
import com.falcon.restaurants.utils.Logger;

import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RestaurantViewModel extends AndroidViewModel {

    private static final String TAG = "RestaurantViewModel";

    private MutableLiveData<List<Restaurant>> restaurantsMutableLiveData;

    private FetchRestaurantsUseCase fetchRestaurantsUseCase;

    public RestaurantViewModel(@androidx.annotation.NonNull Application application,
                               FetchRestaurantsUseCase fetchRestaurantsUseCase) {
        super(application);
        this.fetchRestaurantsUseCase = fetchRestaurantsUseCase;
    }

    public Observable<String> fetch(){
       return fetchRestaurantsUseCase.fetch();
    }

    public LiveData<List<Restaurant>> getByParentId(String parentId) {

        if (restaurantsMutableLiveData == null) {
            restaurantsMutableLiveData = new MutableLiveData<List<Restaurant>>();

            Observer<List<Restaurant>> observer = new Observer<List<Restaurant>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    Logger.log( TAG, "onSubscribe: ");
                }

                @Override
                public void onNext(@NonNull List<Restaurant> categories) {
                    Logger.log( TAG, "onNext: ");
                    restaurantsMutableLiveData.setValue(categories);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Logger.log( TAG, "onError: ");
                }
                @Override
                public void onComplete() {
                    Logger.log( TAG, "onComplete: ");
                }
            };

            fetchRestaurantsUseCase.getByParentId(parentId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }

        return restaurantsMutableLiveData;
    }

    public void hasChildren(String id, LocalListeners.OnSuccessListener listener) {

        fetchRestaurantsUseCase
                .hasChildren(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onSuccess(@NonNull Boolean hasChildren) {
                listener.onSuccess(hasChildren);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                listener.onFailed();
            }
        });
    }

}
