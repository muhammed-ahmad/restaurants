package com.falcon.restaurants.screens.restaurant

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
import com.falcon.restaurants.base.LocalListeners
import com.falcon.restaurants.room.restaurant.Restaurant
import com.falcon.restaurants.screens.common.BaseActivity
import com.falcon.restaurants.screens.common.ImageLoader
import com.falcon.restaurants.screens.common.ScreensNavigator
import com.falcon.restaurants.screens.common.dialogs.DialogsNavigator
import com.falcon.restaurants.utils.Logger

import javax.inject.Inject

class RestaurantsActivity : BaseActivity() {

    val TAG: String = "RestaurantsActivity" 

    var parentId: String = "0"

    @Inject lateinit var restaurantViewModel: RestaurantViewModel
    @Inject lateinit var screensNavigator: ScreensNavigator 
    //@Inject lateinit var dialogsNavigator: DialogsNavigator
    @Inject lateinit var layoutInflator: LayoutInflater
    @Inject lateinit var imageLoader: ImageLoader
    @Inject lateinit var filterRestaurantsUseCase: FilterRestaurantsUseCase

    lateinit var adapter: RestaurantsListAdapter 
    lateinit var searchView: SearchView 

    override fun onCreate(savedInstanceState: Bundle?) {
        presentationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurants)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)

        adapter = RestaurantsListAdapter(
                this,
                layoutInflator,
                filterRestaurantsUseCase,
                imageLoader)

        adapter.onRestaurantClickListener = object: RestaurantsListAdapter.OnRestaurantClickListener{
            override fun onRestaurantClicked(restaurantId: String) {
                launchProperActivity(restaurantId)
            }
        }

        recyclerView.setAdapter(adapter)
        recyclerView.setLayoutManager(LinearLayoutManager(this))

        if(intent.hasExtra(PARENT_ID)){
            parentId = intent.getStringExtra(PARENT_ID)!!
        }
        Logger.log( TAG,"onCreate: parentId: " + parentId)

        setAdapterList()
    }

    fun setAdapterList(){
        restaurantViewModel.getByParentId(parentId).observe(this, {categories ->
                Logger.log(TAG, "setAdapterList: called")
                adapter.main_arrlst = categories as MutableList<Restaurant>
            })
    }

    fun launchProperActivity(restaurantId: String) {
        restaurantViewModel.hasChildren(restaurantId, object : LocalListeners.OnSuccessListener{
            override fun onSuccess(hasChildren: Boolean) {
                Logger.log( TAG, "onSuccess: hasChildren: " + hasChildren)
                if(hasChildren){
                    screensNavigator.toCategoriesActivity(restaurantId)
                }else {
                    screensNavigator.toMealsActivity(restaurantId)
                }
            }
            override fun onFailed() {
                Logger.log( TAG, "onFailed: ")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.menu_main, menu)
        val myActionMenuItem: MenuItem = menu.findItem( R.id.mnuSearch)
        searchView = myActionMenuItem.getActionView() as SearchView
        val searchIcon: ImageView = searchView.findViewById(R.id.search_button)
        searchIcon.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.baseline_search_white_24))
        searchView.setMaxWidth(Integer.MAX_VALUE)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String): Boolean {
                 //Logger.log( "onQueryTextSubmit: ")
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true)
                }
                myActionMenuItem.collapseActionView()
                return false
            }
            override fun onQueryTextChange(s: String): Boolean {
                //Logger.log( "onQueryTextChange: ")
                adapter.filter(s)
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val PARENT_ID: String = "PARENT_ID" 
        fun start(fromActivity: AppCompatActivity, restaurantId: String)
        {
            val intent: Intent = Intent(fromActivity, RestaurantsActivity::class.java)
            intent.putExtra(PARENT_ID, restaurantId)
            fromActivity.startActivity(intent)
            if (restaurantId.equals("0")) {
                fromActivity.finish()
            }
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return true 
    }
}