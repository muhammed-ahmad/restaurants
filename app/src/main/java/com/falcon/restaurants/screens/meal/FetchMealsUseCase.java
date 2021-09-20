package com.falcon.restaurants.screens.meal;

import android.annotation.SuppressLint;
import com.falcon.restaurants.network.meal.FetchMealsEndPoint;
import com.falcon.restaurants.network.meal.MealNet;
import com.falcon.restaurants.room.meal.Meal;
import com.falcon.restaurants.room.meal.MealDao;
import com.falcon.restaurants.utils.Logger;

import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class FetchMealsUseCase {

    private static final String TAG = "FetchMealsUseCase";

    private MealDao mealDao;
    private FetchMealsEndPoint fetchMealsEndPoint;

    @Inject
    public FetchMealsUseCase(MealDao mealDao,
                             FetchMealsEndPoint fetchMealsEndPoint) {
        this.mealDao = mealDao;
        this.fetchMealsEndPoint = fetchMealsEndPoint;
    }

    @SuppressLint("CheckResult")
    public Observable<String> fetch(){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                String maxUpdatedAt = mealDao.getMaxUpdated();

                fetchMealsEndPoint.fetch(maxUpdatedAt, new FetchMealsEndPoint.listener() {
                    @Override
                    public void onFetchSuccess(List<MealNet> mealNets) {
                        Logger.log( TAG, "onFetchSuccess: " + mealNets.size());
                        upsert(mealNets);
                        emitter.onNext("meal completed");
                        emitter.onComplete();
                    }

                    @Override
                    public void onFetchFailed(Throwable e) {
                        Logger.log( TAG, "onFetchFailed: e: " + e.getLocalizedMessage());
                        emitter.onError(e);
                    }
                });
            }
        });
    }

    public Observable<List<Meal>> getByRestaurantId(String typeIdV) {
        return mealDao.getByRestaurantId(typeIdV);
    }

    public Single<Meal> getMealById(String mealId) {
        return mealDao.getMealById(mealId);
    }

    public void upsert(MealNet mealNet) {
            mealDao.upsert(new Meal(
                    mealNet.getId(),
                    mealNet.getName(),
                    mealNet.getDetails(),
                    mealNet.getImageUrl(),
                    mealNet.getRestaurantId(),
                    mealNet.getUpdatedAt(),
                    mealNet.getActive(),
                    mealNet.getFavorite()
            ));
    }

    public void upsert(List<MealNet> mealNets) {
        for (int i = 0; i < mealNets.size(); i++) {
            upsert(mealNets.get(i));
        }
    }
}
