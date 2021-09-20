package com.falcon.restaurants.room.restaurant;

import android.content.Context;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.falcon.restaurants.room.RoomDB;
import com.falcon.restaurants.testdata.RestaurantTestData;
import com.falcon.restaurants.utils.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import java.util.List;
import io.reactivex.observers.TestObserver;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class RestaurantDaoTest {

    private static final String TAG = "RestaurantDaoTest";
    private static final List<Restaurant> RESTAURANTS = RestaurantTestData.createRestaurantsForRoom();
    private static RestaurantDao SUT;
    private static RoomDB db;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @BeforeClass
    public static void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, RoomDB.class).allowMainThreadQueries().build();
        SUT = db.restaurantDao();
        SUT.upsert(RESTAURANTS);
    }

    @Before
    public void buildSpy() {
        SUT = Mockito.spy(db.restaurantDao());
    }

    @AfterClass
    public static void closeDb() {
        db.close();
    }

    @Test
    public void upsert() {
    }

    @Test
    public void getAll() {

        TestObserver<List<Restaurant>> testObserver = SUT.getByParentId("0").test();
        testObserver.assertValue(Restaurants -> Restaurants.size() == 3);
        testObserver.assertValue(Restaurants -> {
            return Restaurants.get(0).getId().equals("id1");
        });
        testObserver.assertValueCount(1);

        SUT.insert(new Restaurant("id6", "0", "name6",  "image_url",
                "1", "1970-01-01 00:00:03"));
        SUT.insert(new Restaurant("id7", "0", "name7",  "image_url",
                "1", "1970-01-01 00:00:03"));

        testObserver.assertValueCount(3);

        List<List<Restaurant>> values = testObserver.values();
        Logger.log( TAG,"getAll: " + values.get(values.size()-1).size()); // get the size of the last one

        testObserver.assertValueAt(0, Restaurants -> Restaurants.size() == 3);
        testObserver.assertValueAt(1, Restaurants -> Restaurants.size() == 4);
        testObserver.assertValueAt(2, Restaurants -> Restaurants.size() == 5);

    }

    @Test
    public void getMaxUpdatedAt() {
        String maxUpdatedAt = SUT.getMaxUpdated();
        assertThat(maxUpdatedAt, equalTo("1970-01-01 00:00:05"));
    }

    @Test
    public void checkExists() {
        boolean isExists =  SUT.checkExists(RESTAURANTS.get(0));
        assertTrue(isExists);
    }

    @Test
    public void CheckNotExists() {
        Restaurant restaurant = new Restaurant("id5", "0", "name5",
                "image_url", "1", "1970-01-01 00:00:03");
        boolean isExists =  SUT.checkExists(restaurant);
        assertFalse(isExists);
    }

    @Test
    public void CheckUpsertWithInsert() {
        Restaurant restaurant = new Restaurant("id8", "0", "name8",
                "image_url", "1", "1970-01-01 00:00:03");
        SUT.upsert(restaurant);
        verify(SUT).insert(restaurant);
        verify(SUT, never()).update(any(Restaurant.class));
    }

    @Test
    public void CheckUpsertWithUpdate() {
        //Mockito.reset(SUT);
        Restaurant restaurant = new Restaurant("id3", "0", "name3",
                "image_url", "1", "1970-01-01 00:00:03");
        SUT.upsert(restaurant);

        verify(SUT).update(restaurant);
        //verify(SUT).insert(restaurant);
        verify(SUT, never()).insert(any(Restaurant.class));
        //verify(SUT).insert(any(Restaurant.class));
    }

}