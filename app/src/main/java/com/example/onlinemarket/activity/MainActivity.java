package com.example.onlinemarket.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.onlinemarket.IOnBackPress;
import com.example.onlinemarket.R;
import com.example.onlinemarket.fragment.CategoriesFragment;
import com.example.onlinemarket.fragment.HomeFragment;
import com.example.onlinemarket.fragment.ShoppingBagFragment;
import com.example.onlinemarket.fragment.UserProfileFragment;
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
                       replaceFragment(UserProfileFragment.newInstance());
                        return true;
                    case R.id.navigation_shoppingBag:
                        replaceFragment(ShoppingBagFragment.newInstance());
                        return true;
                    case R.id.navigation_category:
                        replaceFragment(CategoriesFragment.newInstance());
                        return true;
                    case R.id.navigation_home:
                       replaceFragment(HomeFragment.newInstance());
                        return true;
                    default:
                        throw new IllegalStateException(
                                "Unexpected value: " +
                                        item.getItemId());
                }
            }
        });
    }

    private void replaceFragment(Fragment newFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_main_activity,newFragment)
                .hide(HomeFragment.newInstance())
                .commit();
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().
                findFragmentById(R.id.fragment_container_main_activity);
        if (!(currentFragment instanceof IOnBackPress) ||
                !((IOnBackPress) currentFragment).onBackPressed()) {
            super.onBackPressed();
        }else {

            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.fragment_container_main_activity,
                            HomeFragment.newInstance()).
                    commit();
        }
    }

}