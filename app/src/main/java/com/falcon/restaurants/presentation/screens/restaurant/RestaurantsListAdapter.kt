package com.falcon.restaurants.presentation.screens.restaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.falcon.restaurants.databinding.RecyclerviewRestaurantItemBinding
import com.falcon.restaurants.data.room.restaurant.RestaurantModel
import com.falcon.restaurants.presentation.screens.common.ImageLoader
import com.falcon.restaurants.domain.utils.Constants
import com.falcon.restaurants.presentation.model.RestaurantUiModel

class RestaurantsListAdapter (
    val context: Context,
    val layoutInflater: LayoutInflater,
    val imageLoader: ImageLoader
    ): RecyclerView.Adapter<RestaurantsListAdapter.ModelViewHolder>() {

    val TAG: String = "RestaurantsListAdapter" 

    var main_arrlst = mutableListOf<RestaurantUiModel>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    lateinit var onRestaurantItemClicked: (restaurantId: String) -> Unit

    inner class ModelViewHolder(private val binding: RecyclerviewRestaurantItemBinding) : RecyclerView.ViewHolder(binding.root) {

        lateinit var restaurantId: String

        fun bind(currentRestaurantUiModel: RestaurantUiModel){
            binding.nameTxt.text = currentRestaurantUiModel.name
            restaurantId = currentRestaurantUiModel.id
            imageLoader.loadImage(binding.restaurantImg, Constants.imagesCategoriesUrl + currentRestaurantUiModel.imageUrl)
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

    fun setList(restaurantModels: List<RestaurantUiModel>?) {
        main_arrlst = restaurantModels as MutableList<RestaurantUiModel>
    }

}