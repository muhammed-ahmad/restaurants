package com.falcon.restaurants.presentation.util
import android.content.Context
import android.widget.Toast
import javax.inject.Inject

class ToastHelper @Inject constructor (val context: Context) {

   fun showError(e: Throwable){
        Toast.makeText(context, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show()
    }

    fun showSuccess(){
        Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
    }
}
