package com.falcon.restaurants.presentation.screens.mealdetail
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.falcon.restaurants.databinding.ActivityMealDetailsBinding
import com.falcon.restaurants.data.room.meal.MealModel
import com.falcon.restaurants.presentation.screens.common.BaseActivity
import com.falcon.restaurants.presentation.screens.common.ImageLoader
import com.falcon.restaurants.presentation.screens.meal.MealViewModel
import com.falcon.restaurants.domain.utils.Constants
import com.falcon.restaurants.domain.utils.Logger
import com.falcon.restaurants.presentation.model.MealUiModel
import javax.inject.Inject

class MealDetailsActivity : BaseActivity() {

    val TAG: String = "MealDetailsActivity"
    var mealId: String = ""
    lateinit var binding: ActivityMealDetailsBinding

    @Inject lateinit var mealViewModel: MealViewModel
    @Inject lateinit var imageLoader: ImageLoader
    @Inject lateinit var layoutInflator: LayoutInflater

    companion object {
        const val MEAL_ID: String = "MEAL_ID"
        fun start(fromActivity: AppCompatActivity, mealId: String)
        {
            val intent = Intent(fromActivity, MealDetailsActivity::class.java)
            intent.putExtra(MEAL_ID, mealId)
            fromActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        presentationComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailsBinding.inflate(layoutInflator)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if(intent.hasExtra(MEAL_ID)) {
            mealId = intent.getStringExtra(MEAL_ID)!!
        }

        Logger.log( TAG, "onCreate: mealId: $mealId")

        if(!mealId.isEmpty()) {
            mealViewModel.getMealById(mealId, object :MealViewModel.ListenerForOne {

                override fun onQuerySuccess(mealModel: MealUiModel) {
                    if (mealModel !=null){
                        imageLoader.loadImage(binding.mealImg, Constants.imagesMealsUrl + mealModel.imageUrl)
                        binding.nameTxt.setText(mealModel.name)
                        binding.detailsTxt.setText(mealModel.details)
                    }
                }
                override fun onQueryFailed(e: Throwable) {
                }
            })
        }
    }
}
