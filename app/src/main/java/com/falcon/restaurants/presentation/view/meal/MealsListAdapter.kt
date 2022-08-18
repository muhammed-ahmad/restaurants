package com.falcon.restaurants.presentation.view.meal
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.falcon.restaurants.databinding.RecyclerviewMealItemBinding
import com.falcon.restaurants.presentation.view.common.ImageLoader
import com.falcon.restaurants.data.net.Constants
import com.falcon.restaurants.domain.model.Meal
import com.falcon.restaurants.domain.util.Logger

class MealsListAdapter (
    val context: Context,
    val layoutInflater: LayoutInflater,
    val imageLoader: ImageLoader

    ): RecyclerView.Adapter<MealsListAdapter.ModelViewHolder>() {

    val TAG: String = "MealsListAdapter"

    var main_arrlst = mutableListOf<Meal>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    lateinit var onMealItemClicked : (mealId: String) -> Unit

    inner class ModelViewHolder (private val binding: RecyclerviewMealItemBinding) : RecyclerView.ViewHolder (binding.root){

        lateinit var mealId: String

        fun bind(currentMeal: Meal) {
            binding.nameTxt.text = currentMeal.name
            mealId = currentMeal.id
            Logger.log( TAG, "onBindViewHolder Image_url: " + currentMeal.imageUrl)
            imageLoader.loadImage(binding.mealImg, Constants.imagesMealsUrl + currentMeal.imageUrl)
        }

        init {
            binding.root.setOnClickListener { view -> onMealItemClicked(mealId) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val binding = RecyclerviewMealItemBinding.inflate(layoutInflater, parent, false)
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

    fun setList(meals: List<Meal>?) {
        main_arrlst = meals as MutableList<Meal>
    }

}