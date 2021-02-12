package com.example.onlinemarket.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.onlinemarket.IOnBackPress;
import com.example.onlinemarket.R;
import com.example.onlinemarket.controller.fragment.CategoriesFragment;
import com.example.onlinemarket.controller.fragment.HomeFragment;
import com.example.onlinemarket.controller.fragment.ShoppingBagFragment;
import com.example.onlinemarket.controller.fragment.UserProfileFragment;
import com.example.onlinemarket.utils.UIUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;

    public static Intent newIntent(Context context) {

        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction().
                    add(R.id.fragment_container_main_activity,
                            HomeFragment.newInstance())
                    .commit();
        }
        setListeners();
    }

    private void findViews() {
        mBottomNavigationView=findViewById(R.id.navigation_view_main_activity);
    }
    private void setListeners() {

        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_profile:
                       UIUtils.replaceFragment(getSupportFragmentManager(),
                               UserProfileFragment.newInstance());
                        return true;
                    case R.id.navigation_shoppingBag:
                        UIUtils.replaceFragment(getSupportFragmentManager(),
                                ShoppingBagFragment.newInstance());
                        return true;
                    case R.id.navigation_category:
                        UIUtils.replaceFragment(getSupportFragmentManager(),
                                CategoriesFragment.newInstance());
                        return true;
                    case R.id.navigation_home:
                       UIUtils.replaceFragment(getSupportFragmentManager(),HomeFragment.newInstance());
                        return true;
                    default:
                        throw new IllegalStateException(
                                "Unexpected value: " +
                                        item.getItemId());
                }
            }
        });
    }

   /* private void replaceFragment(Fragment newFragment) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_main_activity,newFragment)
                .hide(HomeFragment.newInstance())
                .commit();
    }*/

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().
                findFragmentById(R.id.fragment_container_main_activity);
        if (!(currentFragment instanceof IOnBackPress) ||
                !((IOnBackPress) currentFragment).onBackPressed()) {
            super.onBackPressed();
        }else {
            UIUtils.replaceFragment(getSupportFragmentManager(),
                    HomeFragment.newInstance());

        }
    }

}