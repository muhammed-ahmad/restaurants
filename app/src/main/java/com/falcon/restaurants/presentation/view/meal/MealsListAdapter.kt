package com.falcon.restaurants.presentation.view.meal
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.falcon.restaurants.databinding.RecyclerviewMealItemBinding
import com.falcon.restaurants.presentation.view.common.ImageLoader
import com.falcon.restaurants.data.net.Constants
import com.falcon.restaurants.domain.util.Logger
import com.falcon.restaurants.presentation.model.MealUiModel

class MealsListAdapter (
    val context: Context,
    val layoutInflater: LayoutInflater,
    val imageLoader: ImageLoader

    ): RecyclerView.Adapter<MealsListAdapter.ModelViewHolder>() {

    val TAG: String = "MealsListAdapter"

    var main_arrlst = mutableListOf<MealUiModel>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    lateinit var onMealItemClicked : (mealId: String) -> Unit

    inner class ModelViewHolder (private val binding: RecyclerviewMealItemBinding) : RecyclerView.ViewHolder (binding.root){

        lateinit var mealId: String

        fun bind(currentMealUiModel: MealUiModel) {
            binding.nameTxt.text = currentMealUiModel.name
            mealId = currentMealUiModel.id
            Logger.log( TAG, "onBindViewHolder Image_url: " + currentMealUiModel.imageUrl)
            imageLoader.loadImage(binding.mealImg, Constants.imagesMealsUrl + currentMealUiModel.imageUrl)
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

    fun setList(mealModels: List<MealUiModel>?) {
        main_arrlst = mealModels as MutableList<MealUiModel>
    }

}