package com.falcon.restaurants.screens.restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.falcon.restaurants.R;
import com.falcon.restaurants.room.restaurant.Restaurant;
import com.falcon.restaurants.screens.common.ImageLoader;
import com.falcon.restaurants.utils.Constants;
import java.util.List;

public class RestaurantsListAdapter extends RecyclerView.Adapter<RestaurantsListAdapter.ModelViewHolder> {

    String TAG = "RestaurantsListAdapter" ;
    private List<Restaurant> main_arrlst;

    Context context ;
    private final LayoutInflater layoutInflater;
    private FilterRestaurantsUseCase filterRestaurantsUseCase;
    private ImageLoader imageLoader;

    private OnRestaurantClickListener onRestaurantClickListener;

    public interface OnRestaurantClickListener {
        void onRestaurantClicked(String typeId);
    }

    public RestaurantsListAdapter(Context context,
                                  LayoutInflater layoutInflater,
                                  FilterRestaurantsUseCase filterRestaurantsUseCase,
                                  ImageLoader imageLoader) {
        this.context = context;
        this.layoutInflater = layoutInflater ;
        this.filterRestaurantsUseCase = filterRestaurantsUseCase;
        this.imageLoader = imageLoader;
    }

    class ModelViewHolder extends RecyclerView.ViewHolder {
        private final TextView name_txt;
        private final ImageView restaurant_img;

        private ModelViewHolder(View itemView) {
            super(itemView);
            name_txt = itemView.findViewById(R.id.name_txt);
            restaurant_img = itemView.findViewById(R.id.restaurant_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String typeId = (String) name_txt.getTag();
                    onRestaurantClickListener.onRestaurantClicked(typeId);
                }
            });
        }
    }

    @Override
    public ModelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.recyclerview_restaurant_item, parent, false);
        return new ModelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ModelViewHolder holder, int position) {
        if (main_arrlst != null) {
            Restaurant current = main_arrlst.get(position);
            holder.name_txt.setText(current.getName());
            holder.name_txt.setTag(current.getId());

            imageLoader.loadImage(holder.restaurant_img,
                    Constants.imagesCategoriesUrl + current.getImageUrl());
        }
    }

    public void setMainList(List<Restaurant> main_arrlst){
        if(main_arrlst != null) {
            this.main_arrlst = main_arrlst;
            filterRestaurantsUseCase.setList(main_arrlst);
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
        main_arrlst = filterRestaurantsUseCase.filter(queryText);
        notifyDataSetChanged();
    }

    public void setOnRestaurantClickListener(OnRestaurantClickListener onRestaurantClickListener) {
        this.onRestaurantClickListener = onRestaurantClickListener;
    }
}