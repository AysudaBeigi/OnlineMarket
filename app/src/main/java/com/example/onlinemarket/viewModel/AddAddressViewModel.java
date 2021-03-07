package com.example.onlinemarket.viewModel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class AddAddressViewModel extends AndroidViewModel {
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private MutableLiveData<Location> mUserLocation;

    public AddAddressViewModel(@NonNull Application application) {
        super(application);
        mFusedLocationProviderClient= LocationServices.
                getFusedLocationProviderClient(getApplication());
        mUserLocation=new MutableLiveData<>();

    }

    public LiveData<Location> getUserLocation() {
        return mUserLocation;
    }

    public boolean isPermissionDenied(){
        return ContextCompat.checkSelfPermission(getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getApplication(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED;
    }
    public boolean isLocationEnable() {
        LocationManager locationManager = (LocationManager)
                getApplication().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    public void  requestLocation(){
        LocationRequest locationRequest=LocationRequest.create();
        locationRequest.setNumUpdates(1);
        locationRequest.setInterval(0);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationCallback locationCallback=new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                mUserLocation.setValue(locationResult.getLocations().get(0));
            }
        };

        mFusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper());

    }


}
