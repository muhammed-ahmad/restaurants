package com.falcon.restaurants.presentation.screens.common
import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import javax.inject.Inject

class ImageLoader @Inject constructor( val context: Context) {

    fun loadImage(imageView: ImageView, imageUrl: String) {
        Glide.with(context).load(imageUrl)
                .thumbnail(0.5f)
                .into(imageView)
    }

    fun loadImage(imageView: ImageView, imageUri: Uri) {
        Glide.with(context)
                .load(imageUri)
                .into(imageView)
    }
}