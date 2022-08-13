package com.falcon.restaurants.presentation.view.meal
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.falcon.restaurants.R
import com.falcon.restaurants.databinding.ActivityMealsBinding
import com.falcon.restaurants.presentation.view.common.BaseActivity
import com.falcon.restaurants.presentation.view.common.ImageLoader
import com.falcon.restaurants.presentation.view.common.ScreensNavigator
import com.falcon.restaurants.domain.util.Logger
import javax.inject.Inject

class MealsActivity : BaseActivity() {

    private var restaurantId: String = ""
    lateinit var adapter: MealsListAdapter
    lateinit var binding: ActivityMealsBinding
    @Inject lateinit var mealViewModel: MealViewModel
    @Inject lateinit var screensNavigator: ScreensNavigator 
    @Inject lateinit var layoutInflator: LayoutInflater
    @Inject lateinit var imageLoader: ImageLoader

    companion object{
        const val RESTAURANT_ID: String = "RESTAURANT_ID"
        const val TAG: String = "MealsActivity"
        fun start(fromActivity: AppCompatActivity, restaurantId: String) {
            Logger.log(TAG,"start: ")
            val intent = Intent(fromActivity, MealsActivity::class.java)
            intent.putExtra(RESTAURANT_ID, restaurantId)
            fromActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        presentationComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMealsBinding.inflate(layoutInflator)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if(intent.hasExtra(RESTAURANT_ID)) {
            restaurantId = intent.getStringExtra(RESTAURANT_ID)!!
        }
        Logger.log( TAG,"onCreate: typeId: $restaurantId")

        adapter = MealsListAdapter(this,
                                    layoutInflator,
                                    imageLoader)

        adapter.onMealItemClicked = { mealId -> screensNavigator.toMealDetailsActivity(mealId) }

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        setAdapterList()

    }

    fun setAdapterList() {
        mealViewModel.getByRestaurantId(restaurantId).observe(this, { meals ->
            adapter.setList(meals)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean{
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
    }

}