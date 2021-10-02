package com.falcon.restaurants.screens.meal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.falcon.restaurants.R
import com.falcon.restaurants.room.meal.Meal
import com.falcon.restaurants.screens.common.BaseActivity
import com.falcon.restaurants.screens.common.ImageLoader
import com.falcon.restaurants.screens.common.ScreensNavigator
import com.falcon.restaurants.utils.Logger
import javax.inject.Inject

class MealsActivity : BaseActivity() {

    private var restaurantId: String = "" 

    companion object{
        val RESTAURANT_ID: String = "RESTAURANT_ID"
        val TAG: String = "MealsActivity" 
        fun start(fromActivity: AppCompatActivity, restaurantId: String) {
            Logger.log(TAG,"start: ")
            val intent: Intent = Intent(fromActivity, MealsActivity::class.java)
            intent.putExtra(MealsActivity.RESTAURANT_ID, restaurantId)
            fromActivity.startActivity(intent)
        }
    }

    @Inject lateinit var mealViewModel: MealViewModel
    @Inject lateinit var screensNavigator: ScreensNavigator 
    @Inject lateinit var layoutInflator: LayoutInflater
    @Inject lateinit var imageLoader: ImageLoader
    @Inject lateinit var filterMealsUseCase: FilterMealsUseCase

    lateinit var adapter: MealsListAdapter 
    lateinit var searchView: SearchView 


    override fun onCreate(savedInstanceState: Bundle?) {
        presentationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meals)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        if(intent.hasExtra(RESTAURANT_ID)) {
            restaurantId = intent.getStringExtra(RESTAURANT_ID)!!
        }
        Logger.log( TAG,"onCreate: typeId: " + restaurantId)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)

        adapter = MealsListAdapter(this,
                layoutInflator,
                filterMealsUseCase,
                imageLoader)

        adapter.onMealClickListener =  object: MealsListAdapter.OnMealClickListener{
            override fun onMealClicked(mealId: String) {
                screensNavigator.toMealDetailsActivity(mealId)
            }
        }
        recyclerView.setAdapter(adapter)
        recyclerView.setLayoutManager(LinearLayoutManager(this))

        setAdapterList()

    }

    fun setAdapterList() {
        mealViewModel.getByRestaurantId(restaurantId).observe(this, {meals ->
                adapter.main_arrlst = meals as MutableList<Meal>
        })
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean{
        menuInflater.inflate(R.menu.menu_main, menu)

        val myActionMenuItem: MenuItem = menu.findItem( R.id.mnuSearch)
        searchView = myActionMenuItem.getActionView() as SearchView
        val searchIcon: ImageView = searchView.findViewById(R.id.search_button)
        searchIcon.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.baseline_search_white_24))
        searchView.setMaxWidth(Integer.MAX_VALUE)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true)
                }
                myActionMenuItem.collapseActionView()
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                adapter.filter(s)
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == R.id.mnuSearch) {
            Logger.log( TAG,"onQueryTextSubmit: ")
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
    }

}