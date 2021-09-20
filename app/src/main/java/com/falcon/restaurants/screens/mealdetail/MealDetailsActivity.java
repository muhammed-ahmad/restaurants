package com.falcon.restaurants.screens.mealdetail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.falcon.restaurants.R;
import com.falcon.restaurants.room.meal.Meal;
import com.falcon.restaurants.screens.common.BaseActivity;
import com.falcon.restaurants.screens.common.ImageLoader;
import com.falcon.restaurants.screens.meal.MealViewModel;
import com.falcon.restaurants.utils.Constants;
import com.falcon.restaurants.utils.Logger;

import javax.inject.Inject;

public class MealDetailsActivity extends BaseActivity {

    String TAG = "MealDetailsActivity" ;

    String mealId = "" ;
    public static final String MEAL_ID = "MEAL_ID";

    @Inject public MealViewModel mealViewModel;
    @Inject public ImageLoader imageLoader;

    private ImageView meal_img;
    private TextView name_txt;
    private TextView details_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getPresentationComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        meal_img = findViewById(R.id.meal_img);
        name_txt = findViewById(R.id.name_txt);
        details_txt = findViewById(R.id.details_txt);

        mealId = getIntent().getStringExtra(MealDetailsActivity.MEAL_ID);
        Logger.log( TAG, "onCreate: mealId: " + mealId);

        if(!mealId.isEmpty()) {
            mealViewModel.getMealById(mealId, new MealViewModel.ListenerForOne() {
                @Override
                public void onQuerySuccess(Meal meal) {
                    if (meal !=null){
                        imageLoader.loadImage(meal_img, Constants.imagesMealsUrl + meal.getImageUrl());

                        name_txt.setText(meal.getName());
                        details_txt.setText(meal.getDetails());
                    }
                }
                @Override
                public void onQueryFailed(Throwable e) {
                }
            });
        }
    }

    public static void start(AppCompatActivity fromActivity, String mealId){
        Intent intent = new Intent(fromActivity, MealDetailsActivity.class);
        intent.putExtra(MealDetailsActivity.MEAL_ID, mealId );
        fromActivity.startActivity(intent);
    }

}
