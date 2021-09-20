package com.falcon.restaurants.network.meal;

import com.falcon.restaurants.network.RetrofitInterface;
import com.falcon.restaurants.utils.Logger;

import java.util.List;
import javax.inject.Inject;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FetchMealsEndPoint {

    private static final String TAG = "FetchMealsEndPoint";

    RetrofitInterface retrofitInterface;

    @Inject
    public FetchMealsEndPoint(RetrofitInterface RetrofitInterface) {
        this.retrofitInterface = RetrofitInterface;
    }

    public interface listener{
        void onFetchSuccess(List<MealNet> mealNets);
        void onFetchFailed(Throwable e);
    }

    public void fetch(String maxUpdatedAt, final FetchMealsEndPoint.listener listener) {

        Single<List<MealNet>> single = retrofitInterface.getMeals(maxUpdatedAt);

        SingleObserver<List<MealNet>> singleObserver = new SingleObserver<List<MealNet>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Logger.log( TAG, "onSubscribe: ");
            }

            @Override
            public void onSuccess(List<MealNet> mealNets) {
                Logger.log( TAG, "onSuccess: ");
                listener.onFetchSuccess(mealNets);
            }

            @Override
            public void onError(Throwable e) {
                Logger.log( TAG, "onError: e: " + e.getLocalizedMessage());
                listener.onFetchFailed(e);
            }
        };

        single.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(singleObserver);
    }

}
