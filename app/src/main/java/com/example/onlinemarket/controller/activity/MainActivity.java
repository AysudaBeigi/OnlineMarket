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
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.onlinemarket.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private NavController mNavController;
    private  AppBarConfiguration mAppBarConfiguration;
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
        Log.d(TAG,"MainActivity + after setContentView");

        /*FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.nav_host_fragment);
        if (fragment == null) {
            Log.d(TAG,"fragment == null");
            fragmentManager
                    .beginTransaction()
                    .add(R.id.nav_host_fragment, HomeFragment.newInstance())
                    .commit();
        }*/
        //findViews();

        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        mBottomNavigationView = findViewById(R.id.button_navigation_view_main_activity);
        Log.d(TAG,"MainActivity + after findViews");

        // val navController=requireActivity().findNavController(R.id.navHostLoggedInView)
       // bottomBarView.setupWithNavController(navController)
        //setupActionBarWithNavController(MainActivity@this, navController())

        mAppBarConfiguration = new AppBarConfiguration.Builder(mNavController.getGraph())
                .build();
        Log.d(TAG,"MainActivity + after new AppBarConfiguration.Builder");

        NavigationUI.setupActionBarWithNavController(this, mNavController);
        Log.d(TAG,"MainActivity + after  setupActionBarWithNavController");

        NavigationUI.setupWithNavController(mBottomNavigationView,mNavController);
        Log.d(TAG,"MainActivity + after  setupWithNavController");
        moveNavigationButton();

/*

        //Set bottom navigation
        mNavController = Navigation.findNavController(this,
                R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(mBinding.bottomNavigation,
                mNavController);
        //Set navigation up button
        mAppBarConfiguration = new AppBarConfiguration.
                Builder(mNavController.getGraph())
                .setOpenableLayout(mBinding.drawerLayout)
                .build();
        NavigationUI.setupActionBarWithNavController(this,
                mNavController,
                mBinding.drawerLayout);

       */

    }
    @Override
    public boolean onSupportNavigateUp() {
        Log.d(TAG,"onSupportNavigateUp");
        //override fun onSupportNavigateUp(): Boolean {
           // return navController().navigateUp() || super.onSupportNavigateUp()
        //}
        return NavigationUI.navigateUp(mNavController, mAppBarConfiguration);
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