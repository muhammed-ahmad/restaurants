package com.falcon.restaurants.screens.meal;

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
import com.falcon.restaurants.screens.common.BaseActivity;
import com.falcon.restaurants.screens.common.ImageLoader;
import com.falcon.restaurants.screens.common.ScreensNavigator;
import com.falcon.restaurants.utils.Logger;

import javax.inject.Inject;

public class MealsActivity extends BaseActivity {

    private static String TAG = "MealsActivity" ;

    private  String restaurantId = "" ;
    public static String RESTAURANT_ID = "RESTAURANT_ID" ;

    @Inject public MealViewModel mealViewModel;
    @Inject public ScreensNavigator screensNavigator ;
    @Inject public LayoutInflater layoutInflater;
    @Inject public ImageLoader imageLoader;
    @Inject public FilterMealsUseCase filterMealsUseCase;

    MealsListAdapter adapter ;
    SearchView searchView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getPresentationComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        restaurantId = intent.getStringExtra(MealsActivity.RESTAURANT_ID);
        Logger.log( TAG,"onCreate: typeId: " + restaurantId);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        adapter = new MealsListAdapter(this,
                layoutInflater,
                filterMealsUseCase,
                imageLoader);

        adapter.setOnMealClickListener(new MealsListAdapter.OnMealClickListener() {
            @Override
            public void onMealClicked(String mealId) {
                screensNavigator.toMealDetailsActivity(mealId);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setAdapterList();

    }

    public void setAdapterList() {
        mealViewModel.getByRestaurantId(restaurantId).observe(this, meals -> {
            adapter.setMainList(meals);
        });
    }

    public static void start(AppCompatActivity fromActivity, String restaurantId) {
        Logger.log( TAG,"start: ");
        Intent intent = new Intent(fromActivity, MealsActivity.class);
        intent.putExtra(MealsActivity.RESTAURANT_ID, restaurantId);
        fromActivity.startActivity(intent);
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
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                adapter.filter(s);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnuSearch) {
            Logger.log( TAG,"onQueryTextSubmit: ");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}