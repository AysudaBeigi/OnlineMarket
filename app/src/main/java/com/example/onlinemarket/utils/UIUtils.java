package com.example.onlinemarket.utils;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.onlinemarket.R;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

public class UIUtils {
    public static String TAG = "OnlineMarket";
    public static Snackbar makeSnackBar(View view, int message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        return snackbar;

    }


    public static void setImageUsingPicasso(String imageSrc, ImageView imageView){
        Log.d(TAG,"setImageUsingPicasso");

        Picasso.get()
                .load(imageSrc)
                .placeholder(R.drawable.ic_placeholder_recycler)
                .into(imageView);

    }

}
