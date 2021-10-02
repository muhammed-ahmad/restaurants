package com.falcon.restaurants.screens.restaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.falcon.restaurants.R
import com.falcon.restaurants.room.restaurant.Restaurant
import com.falcon.restaurants.screens.common.ImageLoader
import com.falcon.restaurants.utils.Constants

class RestaurantsListAdapter (

    val context: Context ,
    val layoutInflater: LayoutInflater,
    val filterRestaurantsUseCase: FilterRestaurantsUseCase,
    val imageLoader: ImageLoader

        ): RecyclerView.Adapter<RestaurantsListAdapter.ModelViewHolder>() {

    val TAG: String = "RestaurantsListAdapter" 

    var main_arrlst = mutableListOf<Restaurant>()
        set(value){
            field = value
            filterRestaurantsUseCase.setList(main_arrlst)
            notifyDataSetChanged()
        }

    lateinit var onRestaurantClickListener: OnRestaurantClickListener

    interface OnRestaurantClickListener {
        fun onRestaurantClicked(typeId: String)
    }

    inner class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name_txt: TextView
        var restaurant_img: ImageView

        init {

            name_txt = itemView.findViewById(R.id.name_txt)
            restaurant_img = itemView.findViewById(R.id.restaurant_img)

            itemView.setOnClickListener(object :View.OnClickListener{
                override fun onClick(view: View) {
                    val typeId: String = name_txt.tag as String
                    onRestaurantClickListener.onRestaurantClicked(typeId)
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val itemView: View = layoutInflater.inflate(R.layout.recyclerview_restaurant_item, parent, false)
        return ModelViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        if (main_arrlst != null) {
            val current: Restaurant = main_arrlst.get(position)
            holder.name_txt.setText(current.name)
            holder.name_txt.setTag(current.id)

            imageLoader.loadImage(holder.restaurant_img,
                    Constants.imagesCategoriesUrl + current.imageUrl)
        }
    }

    override fun getItemCount(): Int {
        if (main_arrlst != null)
            return main_arrlst.size
        else return 0
    }

    fun filter(queryText: String){
        main_arrlst = filterRestaurantsUseCase.filter(queryText)
        notifyDataSetChanged()
    }

}