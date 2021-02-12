package com.example.onlinemarket.utils;

import android.view.View;
import android.widget.ImageView;

import com.example.onlinemarket.R;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

public class UIUtils {
    public static Snackbar makeSnackBar(View view, int message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        return snackbar;

    }

   /* public static void replaceFragment(FragmentManager fragmentManager,Fragment fragment) {

        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_main_activity, fragment)
                .commit();
    }*/
    public static void setImageUsingPicasso(String imageSrc, ImageView imageView){
        Picasso.get()
                .load(imageSrc)
                .placeholder(R.drawable.ic_placeholder_recycler)
                .into(imageView);

    }

}
