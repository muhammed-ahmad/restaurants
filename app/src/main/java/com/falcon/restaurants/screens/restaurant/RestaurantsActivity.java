package com.falcon.restaurants.screens.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.falcon.restaurants.R;
import com.falcon.restaurants.base.LocalListeners;
import com.falcon.restaurants.screens.common.BaseActivity;
import com.falcon.restaurants.screens.common.ImageLoader;
import com.falcon.restaurants.screens.common.ScreensNavigator;
import com.falcon.restaurants.screens.common.dialogs.DialogsNavigator;
import com.falcon.restaurants.utils.Logger;

import javax.inject.Inject;

public class RestaurantsActivity extends BaseActivity {
    String TAG = "RestaurantsActivity" ;

    private String parentId = "0";
    public static String PARENT_ID = "PARENT_ID" ;

    @Inject public RestaurantViewModel restaurantViewModel;
    @Inject public ScreensNavigator screensNavigator ;
    @Inject public DialogsNavigator dialogsNavigator;
    @Inject public LayoutInflater layoutInflater;
    @Inject public ImageLoader imageLoader;
    @Inject public FilterRestaurantsUseCase filterRestaurantsUseCase;

    RestaurantsListAdapter adapter ;
    SearchView searchView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getPresentationComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        adapter = new RestaurantsListAdapter(
                this,
                layoutInflater,
                filterRestaurantsUseCase,
                imageLoader);

        adapter.setOnRestaurantClickListener(new RestaurantsListAdapter.OnRestaurantClickListener() {
            @Override
            public void onRestaurantClicked(String restaurantId) {
                launchProperActivity(restaurantId);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        if(intent.hasExtra(RestaurantsActivity.PARENT_ID)){
            parentId = intent.getStringExtra(RestaurantsActivity.PARENT_ID);
        }
        Logger.log( TAG,"onCreate: parentId: " + parentId);

        setAdapterList();
    }

    public void setAdapterList(){
        restaurantViewModel.getByParentId(parentId).observe(this, categories -> {
            Logger.log( TAG,"setAdapterList: called");
            adapter.setMainList(categories);
        });
    }

    private void launchProperActivity(String restaurantId) {
        restaurantViewModel.hasChildren(restaurantId, new LocalListeners.OnSuccessListener() {
            @Override
            public void onSuccess(Boolean hasChildren) {
                Logger.log( TAG, "onSuccess: hasChildren: " + hasChildren);
                if(hasChildren){
                    screensNavigator.toCategoriesActivity(restaurantId);
                }else {
                    screensNavigator.toMealsActivity(restaurantId);
                }
            }
            @Override
            public void onFailed() {
                Logger.log( TAG, "onFailed: ");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem myActionMenuItem = menu.findItem( R.id.mnuSearch);
        searchView = (SearchView) myActionMenuItem.getActionView();
        ImageView searchIcon = searchView.findViewById(R.id.search_button);
        searchIcon.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.baseline_search_white_24));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                 //Logger.log( "onQueryTextSubmit: ");
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                //Logger.log( "onQueryTextChange: ");
                adapter.filter(s);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public static void start(AppCompatActivity fromActivity, String restaurantId){
        Intent intent = new Intent(fromActivity, RestaurantsActivity.class);
        intent.putExtra(RestaurantsActivity.PARENT_ID, restaurantId);
        fromActivity.startActivity(intent);
        if(restaurantId.equals("0")) {
            fromActivity.finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return true ;
    }
}