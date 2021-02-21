package com.example.onlinemarket.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlinemarket.R;
import com.example.onlinemarket.utils.UIUtils;
import com.example.onlinemarket.viewModel.SplashViewModel;
import com.google.android.material.snackbar.Snackbar;

public class SplashActivity extends AppCompatActivity {

    public static final String TAG = "onlineMarket";
    private SplashViewModel mSplashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSplashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        if (mSplashViewModel.isNotworkConnected(this)) {
            Log.d(TAG, "isNetworkConnected");
            startMainActivityAfterDelay();
        } else {
            Log.d(TAG, "is not NetworkConnected");
            showInternetIsDisconnectedSnackBar();

        }

    }

    private void showInternetIsDisconnectedSnackBar() {
        Log.d(TAG, "showInternetIsDisconnectedSnackBar");

        Snackbar snackbar = UIUtils.makeSnackBar(
                findViewById(R.id.layout_connection_snack_bar),
                R.string.internet_is_disconnected);
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE)
                .setBackgroundTint(Color.WHITE)
                .setTextColor(Color.RED)
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




}