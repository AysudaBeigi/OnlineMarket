package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinemarket.utils.NetworkUtils;

public class SplashViewModel  extends AndroidViewModel {


    private SplashViewModel(@NonNull Application application) {
        super(application);
    }


    public boolean isNotworkConnected(Context context){
        return NetworkUtils.isNetworkConnected(context);
    }
}
