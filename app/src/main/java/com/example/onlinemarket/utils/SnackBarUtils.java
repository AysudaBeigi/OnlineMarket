package com.example.onlinemarket.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackBarUtils {
    public static Snackbar MakeSnackBar(View view,String message){
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        return snackbar;

    }
}
