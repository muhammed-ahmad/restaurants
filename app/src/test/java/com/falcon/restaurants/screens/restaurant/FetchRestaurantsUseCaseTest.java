package com.falcon.restaurants.screens.restaurant;

import com.falcon.restaurants.network.RetrofitInterface;
import com.falcon.restaurants.network.restaurant.FetchRestaurantsEndPoint;
import com.falcon.restaurants.network.restaurant.RestaurantNet;
import com.falcon.restaurants.room.restaurant.Restaurant;
import com.falcon.restaurants.room.restaurant.RestaurantDao;
import com.falcon.restaurants.testdata.RestaurantTestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.observers.TestObserver;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class FetchRestaurantsUseCaseTest {

    private static final List<RestaurantNet> RESTAURANTNETS = RestaurantTestData.createRestaurantNets();
    FetchRestaurantsUseCase SUT ;

    FetchRestaurantsEndPointTd fetchRestaurantsEndPointTd;
    @Mock RestaurantDao restaurantDaoMock;
    @Mock RetrofitInterface RetrofitInterfaceMock;

    @Before
    public void setUp(){
        fetchRestaurantsEndPointTd = new FetchRestaurantsEndPointTd(RetrofitInterfaceMock);
        SUT = new FetchRestaurantsUseCase(
                restaurantDaoMock,
                fetchRestaurantsEndPointTd
        );
    }

    // fetch on success then upsert is called with the correct data and Observable emits upsert_completed
    @Test
    public void fetch_success_upsertWithTheCorrectData() {
        // Arrange
        success();
        // act
        TestObserver<String> testObserver = SUT.fetch().test();
        // assert
        verify(restaurantDaoMock, times(3)).upsert(any(Restaurant.class));
        testObserver.assertValue(string -> string.equals("upsert_completed"));
    }

    // fetch on failed then upsert not called
    @Test
    public void fetch_failure_upsertNotCalled() {
        // Arrange
        failure();
        // act
        SUT.fetch().test();;
        // assert
        verify(restaurantDaoMock, never()).upsert(new ArrayList<>());
    }

    // fetch on failed then Observable emits error
    @Test
    public void fetch_failure_ObservableEmitsError() {
        // Arrange
        failure();
        // act
        TestObserver<String> testObserver = SUT.fetch().test();;
        // assert
        testObserver.assertError(Throwable.class);
    }

    // ******************* helper classes and methods ************************//

    private void success() {}

    private void failure() {
        fetchRestaurantsEndPointTd.failure = true ;
    }

    private static class FetchRestaurantsEndPointTd extends FetchRestaurantsEndPoint {
        public boolean failure;

        public FetchRestaurantsEndPointTd(RetrofitInterface RetrofitInterface) {
            super(RetrofitInterface);
        }

        @Override
        public void fetch(String maxUpdatedAt, Listener listener) {
            if(failure){
                listener.onFetchFailed(new Throwable());
            }else {
                listener.onFetchSuccess(RESTAURANTNETS);
            }
        }
    }
}