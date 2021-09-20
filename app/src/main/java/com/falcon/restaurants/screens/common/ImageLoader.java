package com.falcon.restaurants.screens.common;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import javax.inject.Inject;

public class ImageLoader {

    Context context;

    @Inject
    public ImageLoader(Context context) {
        this.context = context;
    }

    public void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(context).load(imageUrl)
                .thumbnail(0.5f)
                .into(imageView);
    }

    public void loadImage(ImageView imageView, Uri imageUri) {
        Glide.with(context)
                .load(imageUri)
                .into(imageView);
    }
}