package com.falcon.restaurants.screens.meal

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.falcon.restaurants.screens.common.ImageLoader
import com.falcon.restaurants.R
import com.falcon.restaurants.room.meal.Meal
import com.falcon.restaurants.utils.Constants
import com.falcon.restaurants.utils.Logger

class MealsListAdapter (val context: Context,
                        val layoutInflater: LayoutInflater,
                        val filterMealsUseCase: FilterMealsUseCase,
                        val imageLoader: ImageLoader
                        ): RecyclerView.Adapter<MealsListAdapter.ModelViewHolder>() {

    val TAG: String = "MealsListAdapter"

    var main_arrlst = mutableListOf<Meal>()
        set(value){
            field = value
            filterMealsUseCase.setList(main_arrlst)
            notifyDataSetChanged()
        }

    lateinit var onMealClickListener :OnMealClickListener

    interface OnMealClickListener {
       fun onMealClicked(mealId: String)
    }

    inner class ModelViewHolder (itemView: View) : RecyclerView.ViewHolder (itemView){
        val meal_img: ImageView
        val name_txt: TextView
        lateinit var mealId: String

        init {
            meal_img = itemView.findViewById(R.id.meal_img)
            name_txt = itemView.findViewById(R.id.name_txt)

            itemView.setOnClickListener(object :View.OnClickListener{
                override fun onClick(view: View) {
                    onMealClickListener.onMealClicked(mealId)
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val itemView: View = layoutInflater.inflate(R.layout.recyclerview_meal_item, parent, false)
        return ModelViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        if (main_arrlst != null) {
            val current: Meal = main_arrlst.get(position)
            holder.name_txt.setText(current.name)
            holder.mealId = current.id

            Logger.log( TAG, "onBindViewHolder Image_url: " + current.imageUrl)

            imageLoader.loadImage(holder.meal_img, Constants.imagesMealsUrl + current.imageUrl)

        }
    }

    override fun getItemCount(): Int {
        if (main_arrlst != null)
            return main_arrlst.size
        else return 0
    }

    fun filter(queryText: String){
        main_arrlst = filterMealsUseCase.filter(queryText)
        notifyDataSetChanged()
    }
}