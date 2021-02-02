package com.example.onlinemarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (isNetworkConnected()) {
            startMainActivityAfterDelay();
        } else {
            showInternetIsDisconnectedSnackBar();

        }
        
    }

    private void showInternetIsDisconnectedSnackBar() {
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = MainActivity.newIntent(SplashActivity.this);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }


    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}