package com.falcon.restaurants.presentation.view.restaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.falcon.restaurants.databinding.RecyclerviewRestaurantItemBinding
import com.falcon.restaurants.presentation.view.common.ImageLoader
import com.falcon.restaurants.data.net.Constants
import com.falcon.restaurants.domain.model.Restaurant

class RestaurantsListAdapter (
    val context: Context,
    val layoutInflater: LayoutInflater,
    val imageLoader: ImageLoader
    ): RecyclerView.Adapter<RestaurantsListAdapter.ModelViewHolder>() {

    val TAG: String = "RestaurantsListAdapter" 

    var main_arrlst = mutableListOf<Restaurant>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    lateinit var onRestaurantItemClicked: (restaurantId: String) -> Unit

    inner class ModelViewHolder(private val binding: RecyclerviewRestaurantItemBinding) : RecyclerView.ViewHolder(binding.root) {

        lateinit var restaurantId: String

        fun bind(currentRestaurant: Restaurant){
            binding.nameTxt.text = currentRestaurant.name
            restaurantId = currentRestaurant.id
            imageLoader.loadImage(binding.restaurantImg, Constants.imagesCategoriesUrl + currentRestaurant.imageUrl)
        }

        init {
            itemView.setOnClickListener { view -> onRestaurantItemClicked(restaurantId) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val binding = RecyclerviewRestaurantItemBinding.inflate(layoutInflater, parent, false)
        return ModelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        if (main_arrlst != null) {
            holder.bind(main_arrlst.get(position))
        }
    }

    override fun getItemCount(): Int {
        if (main_arrlst != null)
            return main_arrlst.size
        else return 0
    }

    fun setList(restaurants: List<Restaurant>?) {
        main_arrlst = restaurants as MutableList<Restaurant>
    }

}