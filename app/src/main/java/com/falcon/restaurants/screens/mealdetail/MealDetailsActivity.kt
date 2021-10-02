package com.falcon.restaurants.screens.mealdetail

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.falcon.restaurants.R
import com.falcon.restaurants.room.meal.Meal
import com.falcon.restaurants.screens.common.BaseActivity
import com.falcon.restaurants.screens.common.ImageLoader
import com.falcon.restaurants.screens.meal.MealViewModel
import com.falcon.restaurants.utils.Constants
import com.falcon.restaurants.utils.Logger

import javax.inject.Inject

class MealDetailsActivity : BaseActivity() {

    val TAG: String = "MealDetailsActivity" 

    var mealId: String = "" 

    @Inject lateinit var mealViewModel: MealViewModel
    @Inject lateinit var imageLoader: ImageLoader

    private lateinit var meal_img:ImageView
    private lateinit var  name_txt: TextView
    private lateinit var  details_txt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        presentationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_details)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        meal_img = findViewById(R.id.meal_img)
        name_txt = findViewById(R.id.name_txt)
        details_txt = findViewById(R.id.details_txt)

        if(intent.hasExtra(MEAL_ID)) {
            mealId = intent.getStringExtra(MEAL_ID)!!
        }

        Logger.log( TAG, "onCreate: mealId: " + mealId)

        if(!mealId.isEmpty()) {
            mealViewModel.getMealById(mealId, object :MealViewModel.ListenerForOne {

                override fun onQuerySuccess(meal: Meal) {
                    if (meal !=null){
                        imageLoader.loadImage(meal_img, Constants.imagesMealsUrl + meal.imageUrl)

                        name_txt.setText(meal.name)
                        details_txt.setText(meal.details)
                    }
                }
                override fun onQueryFailed(e: Throwable) {
                }
            })
        }
    }

    companion object {
        const val MEAL_ID: String = "MEAL_ID"
        fun start(fromActivity: AppCompatActivity, mealId: String)
        {
            var intent = Intent(fromActivity, MealDetailsActivity::class.java)
            intent.putExtra(MEAL_ID, mealId)
            fromActivity.startActivity(intent)
        }
    }

}
