package com.falcon.restaurants.screens.meal;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.falcon.restaurants.screens.common.ImageLoader;
import com.falcon.restaurants.R;
import com.falcon.restaurants.room.meal.Meal;
import com.falcon.restaurants.utils.Constants;
import com.falcon.restaurants.utils.Logger;

import java.util.List;

public class MealsListAdapter extends RecyclerView.Adapter<MealsListAdapter.ModelViewHolder> {

    private static final String TAG = "MealsListAdapter";
    private List<Meal> main_arrlst;

    Context context ;
    private final LayoutInflater layoutInflater;
    private FilterMealsUseCase filterMealsUseCase;
    private ImageLoader imageLoader;
    private OnMealClickListener onMealClickListener;

    public MealsListAdapter(Context context,
                            LayoutInflater layoutInflater,
                            FilterMealsUseCase filterMealsUseCase,
                            ImageLoader imageLoader) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.filterMealsUseCase = filterMealsUseCase;
        this.imageLoader = imageLoader;
    }

    public interface OnMealClickListener {
        void onMealClicked(String mealId);
    }

    class ModelViewHolder extends RecyclerView.ViewHolder {

        private final ImageView meal_img;
        private final TextView name_txt;
        private String mealId;

        private ModelViewHolder(View itemView) {
            super(itemView);
            meal_img = itemView.findViewById(R.id.meal_img);
            name_txt = itemView.findViewById(R.id.name_txt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onMealClickListener.onMealClicked(mealId);
                }
            });
        }
    }

    @Override
    public ModelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.recyclerview_meal_item, parent, false);
        return new ModelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ModelViewHolder holder, int position) {
        if (main_arrlst != null) {
            Meal current = main_arrlst.get(position);
            holder.name_txt.setText(current.getName());
            holder.mealId = current.getId();

            Logger.log( TAG, "onBindViewHolder Image_url: " + current.getImageUrl());

            imageLoader.loadImage(holder.meal_img, Constants.imagesMealsUrl + current.getImageUrl());

        }
    }

    public void setMainList(List<Meal> main_arrlst){
        if(main_arrlst != null) {
            this.main_arrlst = main_arrlst;
            filterMealsUseCase.setList(main_arrlst);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if (main_arrlst != null)
            return main_arrlst.size();
        else return 0;
    }

    public void filter(String queryText){
        main_arrlst = filterMealsUseCase.filter(queryText);
        notifyDataSetChanged();
    }

    public void setOnMealClickListener(OnMealClickListener onMealClickListener) {
        this.onMealClickListener = onMealClickListener;
    }
}