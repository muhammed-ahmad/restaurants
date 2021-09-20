package com.falcon.restaurants.screens.restaurant;

import android.annotation.SuppressLint;
import com.falcon.restaurants.network.restaurant.FetchRestaurantsEndPoint;
import com.falcon.restaurants.network.restaurant.RestaurantNet;
import com.falcon.restaurants.room.restaurant.Restaurant;
import com.falcon.restaurants.room.restaurant.RestaurantDao;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class FetchRestaurantsUseCase {

    private static final String TAG = "FetchRestaurantsUseCase";

    private RestaurantDao restaurantDao;
    private FetchRestaurantsEndPoint fetchRestaurantsEndPoint;

    @Inject
    public FetchRestaurantsUseCase(RestaurantDao restaurantDao,
                                   FetchRestaurantsEndPoint fetchRestaurantsEndPoint) {
        this.restaurantDao = restaurantDao;
        this.fetchRestaurantsEndPoint = fetchRestaurantsEndPoint;
    }

    @SuppressLint("CheckResult")
    public Observable<String> fetch(){

        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {

                String maxUpdatedAt = restaurantDao.getMaxUpdated();

                // this is for test
                //String maxUpdatedAt = "1970-01-01 00:00:01" ;
                //////

                fetchRestaurantsEndPoint.fetch(maxUpdatedAt, new FetchRestaurantsEndPoint.Listener() {
                    @Override
                    public void onFetchSuccess(List<RestaurantNet> restaurantNets) {
                        System.out.println(TAG + " onFetchSuccess: " + restaurantNets.size());
                        upsert(restaurantNets);
                        emitter.onNext("upsert_completed");
                        emitter.onComplete();
                    }

                    @Override
                    public void onFetchFailed(Throwable e) {
                        System.out.println(TAG + "onFetchFailed: error: " + e.getLocalizedMessage());
                        emitter.onError(e);
                    }
                });
            }
        });
    }

    public Observable<List<Restaurant>> getByParentId(String parent_id) {
        return restaurantDao.getByParentId(parent_id);
    }

    public Single<Boolean> hasChildren(String id) {
        return restaurantDao.hasChildren(id);
    }

    public long upsert(RestaurantNet restaurantNet) {
        return restaurantDao.upsert(new Restaurant(
                restaurantNet.getId(),
                restaurantNet.getParentId(),
                restaurantNet.getName(),
                restaurantNet.getImageUrl(),
                restaurantNet.getActive(),
                restaurantNet.getUpdatedAt()
        ));
    }

    public void upsert(List<RestaurantNet> restaurantNets) {
        for (int i = 0; i < restaurantNets.size(); i++) {
            long rowId = upsert(restaurantNets.get(i));
        }
        notifyInsertSuccess();
    }

    private void notifyInsertSuccess() {
    }

}
