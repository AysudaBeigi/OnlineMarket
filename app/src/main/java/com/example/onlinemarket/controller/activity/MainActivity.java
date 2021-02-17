package com.example.onlinemarket.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.onlinemarket.R;
import com.example.onlinemarket.controller.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private NavController mNavController;
    private  AppBarConfiguration mAppBarConfiguration;
    public static final String TAG = "onlineMarket";

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.nav_host_fragment);
        if (fragment == null) {
            Log.d(TAG,"fragment == null");
            fragmentManager
                    .beginTransaction()
                    .add(R.id.nav_host_fragment, HomeFragment.newInstance())
                    .commit();
        }
        findViews();

        NavigationUI.setupWithNavController(mBottomNavigationView, mNavController);
        mAppBarConfiguration = new AppBarConfiguration.Builder(mNavController.getGraph())
                .build();
        NavigationUI.setupActionBarWithNavController(this,
                mNavController);
        NavigationUI.setupWithNavController(mBottomNavigationView,mNavController);
    }
    @Override
    public boolean onSupportNavigateUp() {
        Log.d(TAG,"onSupportNavigateUp");

        return NavigationUI.navigateUp(mNavController, mAppBarConfiguration);
    }

    /*private void setNavController() {
        NavigationUI.setupWithNavController(mBottomNavigationView,
                mNavController);

        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.CategoriesFragment:
                                Navigation.findNavController(
                                        MainActivity.this,
                                        R.id.nav_host_fragment).
                                        navigate(R.id.CategoriesFragment);
                                break;
                            case R.id.homeFragment:
                                Navigation.findNavController(
                                        MainActivity.this,
                                        R.id.nav_host_fragment).navigate(R.id.homeFragment);
                                break;
                        }
                        return true;
                    }
                });
    }

*/

    private void findViews() {
        mBottomNavigationView = findViewById(R.id.button_navigation_view_main_activity);
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);

    }


}