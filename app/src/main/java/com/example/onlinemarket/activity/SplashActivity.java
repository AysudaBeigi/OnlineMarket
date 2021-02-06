package com.example.onlinemarket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.onlinemarket.R;
import com.google.android.material.snackbar.Snackbar;

public class SplashActivity extends AppCompatActivity {

    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (isNetworkConnected()) {
            Log.d(TAG, "isNetworkConnected");
            startMainActivityAfterDelay();
        } else {
            Log.d(TAG, "is not NetworkConnected");
            showInternetIsDisconnectedSnackBar();

        }

    }

    private void showInternetIsDisconnectedSnackBar() {
        Log.d(TAG, "showInternetIsDisconnectedSnackBar");

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.layout_connection_snack_bar),
                        R.string.internet_is_disconnected,
                        Snackbar.LENGTH_INDEFINITE)
                .setBackgroundTint(Color.WHITE)
                .setAction(R.string.try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recreate();
                    }
                });
        snackbar.show();
    }


    private void startMainActivityAfterDelay() {
        Log.d(TAG, "startMainActivityAfterDelay");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run");
                Intent intent = MainActivity.newIntent(SplashActivity.this);
                startActivity(intent);
                finish();
            }
        }, 6000);
    }


    private boolean isNetworkConnected() {
        Log.d(TAG, "this is in isNetworkConnected");

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}