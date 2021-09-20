package com.falcon.restaurants.network.restaurant;

import com.falcon.restaurants.network.RetrofitInterface;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FetchRestaurantsEndPoint {

    private static final String TAG = "FetchRestaurantEndPoint";

    RetrofitInterface retrofitInterface;

    @Inject
    public FetchRestaurantsEndPoint(RetrofitInterface RetrofitInterface) {
        this.retrofitInterface = RetrofitInterface;
    }

    public interface Listener {
        void onFetchSuccess(List<RestaurantNet> restaurantNets);
        void onFetchFailed(Throwable e);
    }

    public void fetch(String maxUpdatedAt, final Listener listener) {

        Single<List<RestaurantNet>> single = retrofitInterface.getRestaurants(maxUpdatedAt);
        SingleObserver<List<RestaurantNet>> singleObserver = new SingleObserver<List<RestaurantNet>>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println(TAG + "onSubscribe: ");
            }

            @Override
            public void onSuccess(List<RestaurantNet> restaurantNets) {
                System.out.println(TAG + "onSuccess: ");
                listener.onFetchSuccess(restaurantNets);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(TAG + "onError: " + e.getLocalizedMessage());
                listener.onFetchFailed(e);
            }
        };

        single.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(singleObserver);
    }
}
