package com.example.onlinemarket.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.onlinemarket.IOnBackPress;
import com.example.onlinemarket.R;
import com.example.onlinemarket.fragment.CategoryFragment;
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
            getSupportFragmentManager().beginTransaction().
                    add(R.id.fragment_container_main_activity,
                            HomeFragment.newInstance())
                    .commit();
        }
        moveNavigationButton();
    }

    private void findViews() {
        mBottomNavigationView=findViewById(R.id.navigation_view_main_activity);
    }
    private void moveNavigationButton() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_shoppingBag:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container_main_activity,
                                        ShoppingBagFragment.newInstance())
                                .hide(HomeFragment.newInstance())
                                .commit();
                        return true;
                    case R.id.navigation_profile:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container_main_activity,
                                        UserProfileFragment.newInstance())
                                .hide(HomeFragment.newInstance())
                                .commit();
                        return true;
                    case R.id.navigation_category:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container_main_activity,
                                        CategoryFragment.newInstance())
                                .hide(HomeFragment.newInstance())

                                .commit();
                        return true;

                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container_main_activity,
                                        HomeFragment.newInstance())
                                .hide(HomeFragment.newInstance())

                                .commit();
                        return true;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().
                findFragmentById(R.id.fragment_container_main_activity);
        if (!(fragment instanceof IOnBackPress) || !((IOnBackPress) fragment).onBackPressed()) {
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