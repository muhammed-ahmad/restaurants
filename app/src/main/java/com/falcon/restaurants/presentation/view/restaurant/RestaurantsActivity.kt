package com.falcon.restaurants.presentation.view.restaurant

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.falcon.restaurants.R
import com.falcon.restaurants.databinding.ActivityRestaurantsBinding
import com.falcon.restaurants.presentation.view.common.BaseActivity
import com.falcon.restaurants.presentation.view.common.ImageLoader
import com.falcon.restaurants.presentation.view.common.ScreensNavigator
import com.falcon.restaurants.domain.util.Logger
import javax.inject.Inject

class RestaurantsActivity : BaseActivity() {

    val TAG: String = "RestaurantsActivity"
    lateinit var adapter: RestaurantsListAdapter
    lateinit var binding: ActivityRestaurantsBinding

    @Inject lateinit var restaurantViewModel: RestaurantViewModel
    @Inject lateinit var screensNavigator: ScreensNavigator 
    //@Inject lateinit var dialogsNavigator: DialogsNavigator
    @Inject lateinit var layoutInflator: LayoutInflater
    @Inject lateinit var imageLoader: ImageLoader

    companion object {
        const val PARENT_ID: String = "PARENT_ID"
        fun start(fromActivity: AppCompatActivity)
        {
            val intent = Intent(fromActivity, RestaurantsActivity::class.java)
            fromActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        presentationComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantsBinding.inflate(layoutInflator)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        adapter = RestaurantsListAdapter(
                this,
                layoutInflator,
                imageLoader)

        adapter.onRestaurantItemClicked = { restaurantId -> screensNavigator.toMealsActivity(restaurantId) }

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        setAdapterList()
    }

    fun setAdapterList(){
        restaurantViewModel.getRestaurants().observe(
            this,
                   { restaurants ->
                            Logger.log(TAG, "setAdapterList: called")
                            adapter.setList(restaurants)
                    }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return true 
    }
}