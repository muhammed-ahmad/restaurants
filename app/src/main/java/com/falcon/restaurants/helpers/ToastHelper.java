package com.falcon.restaurants.helpers;

import android.content.Context;
import android.widget.Toast;
import javax.inject.Inject;

public class ToastHelper {

    private static final String TAG = "ToastHelper";
    Context context;

    @Inject
    public ToastHelper(Context context) {
        this.context = context;
    }

    public void showError(Throwable e){
        Toast.makeText(context, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    public void showSuccess(){
        Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
    }
}
