package com.example.onlinemarket.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.ActivitySplashBinding;
import com.example.onlinemarket.utils.UIUtils;
import com.example.onlinemarket.viewModel.SplashViewModel;
import com.google.android.material.snackbar.Snackbar;

public class SplashActivity extends AppCompatActivity {

    public static final String TAG = "onlineMarket";
    private SplashViewModel mSplashViewModel;
    private ActivitySplashBinding mSplashBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "SplashActivity +++ onCreate");

        mSplashBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_splash);
        Log.d(TAG, "SplashActivity +++ after + DataBindingUtil");

        mSplashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        Log.d(TAG, "SplashActivity +++ after + ViewModelProvider");

        if (mSplashViewModel.isNotworkConnected(this)) {
            Log.d(TAG, "isNetworkConnected");

            setRequiredLiveDatas();

            startMainActivityAfterDelay();
        } else {
            Log.d(TAG, "is not NetworkConnected");
            showInternetIsDisconnectedSnackBar();

        }

    }

    private void setRequiredLiveDatas() {
        Log.d(TAG, "SplashActivity +++ setRequiredLiveDatas");

        mSplashViewModel.setSpecialProductLiveData();
        mSplashViewModel.setAmazingOfferProductsLiveData();
        mSplashViewModel.setLatestProductsLiveData();
        mSplashViewModel.setMostVisitedProductsLiveData();
        mSplashViewModel.setPopularProductsLivData();
        mSplashViewModel.setCategoriesLiveData();
    }

    private void showInternetIsDisconnectedSnackBar() {
        Log.d(TAG, "showInternetIsDisconnectedSnackBar");

        Snackbar snackbar = UIUtils.makeSnackBar(
                mSplashBinding.layoutConnectionSnackBar,
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