package com.example.onlinemarket.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.onlinemarket.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private NavController mNavController;
    private static String TAG="OnlineMarket";

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"MainActivity + onCreate");

        setContentView(R.layout.activity_main);

        findViews();
        NavigationUI.setupWithNavController(mBottomNavigationView,mNavController);
        moveNavigationButton();

    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.d(TAG,"onSupportNavigateUp");

        return mNavController.navigateUp();
    }

    private void moveNavigationButton() {

        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        mNavController.navigate(R.id.HomeFragment);
                        return true;
                    case R.id.navigation_category:
                        mNavController.navigate(R.id.CategoriesFragment);
                        return true;

                    case R.id.navigation_shoppingBag:
                        mNavController.navigate(R.id.ShoppingBagFragment);
                        return true;

                    case R.id.navigation_profile:
                        mNavController.navigate(R.id.UserProfileFragment);
                        return true;

                    default:
                        throw new IllegalStateException("Unexpected value: "
                                + item.getItemId());
                }
            }
        });
    }



    private void findViews() {
        Log.d(TAG,"MainActivity + findViews");

        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        mBottomNavigationView = findViewById(R.id.button_navigation_view_main_activity);

    }


}