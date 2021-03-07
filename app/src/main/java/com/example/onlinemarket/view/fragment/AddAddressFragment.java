package com.example.onlinemarket.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.onlinemarket.R;
import com.example.onlinemarket.viewModel.AddAddressViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddAddressFragment extends SupportMapFragment {


    public static final int REQUEST_CODE_PERMISSION_LOCATION = 0;
    private AddAddressViewModel mAddAddressViewModel;
    private GoogleMap mGoogleMap;
    private Marker mMarker;
    private NavController mNavController;
    private MaterialButton mButtonRegister;


    public AddAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddAddressViewModel = new ViewModelProvider(this).
                get(AddAddressViewModel.class);

        mAddAddressViewModel.getUserLocation()
                .observe(this, new Observer<Location>() {
                    @Override
                    public void onChanged(Location location) {
                        updateUI();
                    }
                });
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                updateUI();
            }
        });
        if (hasLocationAccess()) {
            if (!mAddAddressViewModel.isLocationEnable()) {
                createAndShowChangeLocationSettingDialog();
            } else {
                requestLocation();
            }
        } else {
            requestLocationAccessPermission();
        }



    }


    private void createAndShowChangeLocationSettingDialog() {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.location_not_found_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(
                                new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }


    private boolean hasLocationAccess() {

        if (mAddAddressViewModel.isPermissionDenied()) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                createAndShowRequestPermissionRationalDialog();
            }
            return false;
        } else {
            return true;
        }
    }

    private void createAndShowRequestPermissionRationalDialog() {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.why_need_location_permission)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestLocationAccessPermission();
                    }
                })
                .create()
                .show();
    }

    private void requestLocationAccessPermission() {
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        requestPermissions(permissions, REQUEST_CODE_PERMISSION_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_LOCATION:
                if (grantResults == null || grantResults.length == 0)
                    return;
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocation();
                }
                return;
        }
    }

    @SuppressLint("MissingPermission")
    private void requestLocation() {
        if (!hasLocationAccess())
            return;

        mAddAddressViewModel.requestLocation();

    }

    private void updateUI() {

        Location userLocation = mAddAddressViewModel.getUserLocation().getValue();
        if (userLocation == null || mGoogleMap == null)
            return;

        LatLng userLatLng = new LatLng(userLocation.getLatitude(),
                userLocation.getLongitude());

        createAndAddMarker(userLatLng);
        animateCamera(getLatLngBounds(userLatLng));

        setMapListener();
    }

    private LatLngBounds getLatLngBounds(LatLng userLatLng) {
        return new LatLngBounds.Builder()
                .include(userLatLng)
                .build();
    }

    private void setMapListener() {
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                List<Address> locationAddresses = new ArrayList<>();
                try {
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
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
                        mAddAddressViewModel.
                                setUnRegisteredAddress(addressStringBuilder.toString());
                    }

                if (mMarker != null) {
                    mMarker.remove();
                }
                createAndAddMarker(point);
                animateCamera(getLatLngBounds(point));
            }
        });
    }

    private void animateCamera(LatLngBounds latLngBounds) {

        int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, margin);
        mGoogleMap.animateCamera(cameraUpdate);
    }

    private void createAndAddMarker(LatLng latLng) {
        MarkerOptions userMarkerOptions = new MarkerOptions().
                position(latLng)
                .title("شما اینجا هستید");
        mMarker = mGoogleMap.addMarker(userMarkerOptions);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController= Navigation.findNavController(view);
    }
}