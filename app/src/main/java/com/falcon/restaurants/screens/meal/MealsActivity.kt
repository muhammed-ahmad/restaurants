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
import com.falcon.restaurants.databinding.ActivityMealsBinding
import com.falcon.restaurants.databinding.ActivitySplashBinding
import com.falcon.restaurants.room.meal.Meal
import com.falcon.restaurants.screens.common.BaseActivity
import com.falcon.restaurants.screens.common.ImageLoader
import com.falcon.restaurants.screens.common.ScreensNavigator
import com.falcon.restaurants.utils.Logger
import javax.inject.Inject

class MealsActivity : BaseActivity() {

    private var restaurantId: String = ""
    lateinit var adapter: MealsListAdapter
    lateinit var binding: ActivityMealsBinding

    @Inject lateinit var mealViewModel: MealViewModel
    @Inject lateinit var screensNavigator: ScreensNavigator 
    @Inject lateinit var layoutInflator: LayoutInflater
    @Inject lateinit var imageLoader: ImageLoader
    @Inject lateinit var filterMealsUseCase: FilterMealsUseCase

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
                                    filterMealsUseCase,
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
        val myActionMenuItem: MenuItem = menu.findItem( R.id.mnuSearch)
        val searchView: SearchView = myActionMenuItem.actionView as SearchView
        val searchIcon: ImageView = searchView.findViewById(R.id.search_button)
        searchIcon.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.baseline_search_white_24))
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                if( ! searchView.isIconified) {
                    searchView.isIconified = true
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