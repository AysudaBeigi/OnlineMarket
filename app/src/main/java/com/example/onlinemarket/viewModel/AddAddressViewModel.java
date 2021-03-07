package com.example.onlinemarket.viewModel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.data.model.customer.Address;
import com.example.onlinemarket.data.repository.AddressRepository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddAddressViewModel extends AndroidViewModel {
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private MutableLiveData<Location> mUserLocation;
    private AddressRepository mAddressRepository;

    public AddAddressViewModel(@NonNull Application application) {
        super(application);
        mFusedLocationProviderClient = LocationServices.
                getFusedLocationProviderClient(getApplication());
        mUserLocation = new MutableLiveData<>();
        mAddressRepository = AddressRepository.getInstance(getApplication());

    }

    public LiveData<Location> getUserLocation() {
        return mUserLocation;
    }

    public boolean isPermissionDenied() {
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
    public void requestLocation() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setNumUpdates(1);
        locationRequest.setInterval(0);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationCallback locationCallback = new LocationCallback() {
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

    public void setUnRegisteredAddress(String information) {
        Address userAddress = new Address();
        userAddress.setInformation(information);
        mAddressRepository.setUnRegisteredAddress(userAddress);
    }

    public void setUnregisteredAddress(LatLng point) {
        List<android.location.Address> locationAddresses = new ArrayList<>();
        try {
            Geocoder geocoder = new Geocoder(getApplication(), Locale.getDefault());
            locationAddresses = geocoder.getFromLocation(
                    point.latitude,
                    point.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (locationAddresses.size() > 0)
            if (locationAddresses.get(0) != null) {
                StringBuilder addressStringBuilder = new StringBuilder();
                for (int i = 0; i < locationAddresses.get(0).getMaxAddressLineIndex(); i++) {
                    addressStringBuilder.append(locationAddresses.get(0).getAddressLine(i) + "\n");
                }
                setUnRegisteredAddress(addressStringBuilder.toString());
            }
    }
    public LatLngBounds getLatLngBounds(LatLng latLng) {
        return new LatLngBounds.Builder()
                .include(latLng)
                .build();
    }

}
