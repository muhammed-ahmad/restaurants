package com.falcon.restaurants.screens.meal;

import android.annotation.SuppressLint;
import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.falcon.restaurants.room.meal.Meal;
import com.falcon.restaurants.utils.Logger;

import java.util.List;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MealViewModel extends AndroidViewModel {

    private static final String TAG = "MealViewModel";

    private FetchMealsUseCase fetchMealsUseCase;

    private MutableLiveData<List<Meal>> mealsMutableLiveData;

    public MealViewModel(@androidx.annotation.NonNull Application application, FetchMealsUseCase fetchMealsUseCase) {
        super(application);
        this.fetchMealsUseCase = fetchMealsUseCase;
    }

    public interface Listener{
        void onQuerySuccess(List<Meal> meals);
        void onQueryFailed(Throwable e);
    }

    public interface ListenerForOne{
        void onQuerySuccess(Meal meal);
        void onQueryFailed(Throwable e);
    }

    public Observable<String> fetch(){
        return fetchMealsUseCase.fetch();
    }

    @SuppressLint("CheckResult")
    public LiveData<List<Meal>> getByRestaurantId(String restaurantId) {
        if(mealsMutableLiveData == null) {
                mealsMutableLiveData = new MutableLiveData<List<Meal>>();
               fetchMealsUseCase.getByRestaurantId(restaurantId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            meals -> {
                                Logger.log( TAG, "onNext: " + meals.size());
                                mealsMutableLiveData.setValue(meals);
                                },
                            error -> Logger.log(TAG,  "onError: " + error.getLocalizedMessage()),
                            () -> Logger.log( TAG, "onComplete: "),
                            disposable -> Logger.log( TAG, "onSubscribe: "));
        }
        return mealsMutableLiveData;
    }

    public void getMealById(String mealId, ListenerForOne listener) {

        SingleObserver<Meal> singleObserver = new SingleObserver<Meal>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onSuccess(@NonNull Meal meal) {
                listener.onQuerySuccess(meal);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                listener.onQueryFailed(e);
            }
        };

        fetchMealsUseCase.getMealById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleObserver);
    }

}
