package com.example.onlinemarket.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
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

public class AddAddressFragment extends SupportMapFragment {


    public static final int REQUEST_CODE_PERMISSION_LOCATION = 0;
    private AddAddressViewModel mAddAddressViewModel;
    private GoogleMap mGoogleMap;
    private Marker mMarker;
    private NavController mNavController;
    private static String TAG = "OnlineMarket";


    public AddAddressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"AddAddressFragment + onCreate");

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
                    Log.d(TAG,"onMapReady  ");

                    mGoogleMap = googleMap;
                    updateUI();
                }
            });
        if (hasLocationAccess()) {
            Log.d(TAG,"hasLocationAccess is true ");

            getLocation();
        } else {
            Log.d(TAG,"hasLocationAccess is flase ");

            requestLocationAccessPermission();
        }

    }


    private void createAndShowChangeLocationSettingDialog() {
        Log.d(TAG,"createAndShowChangeLocationSettingDialog  ");

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
        Log.d(TAG,"hasLocationAccess ");
        return mAddAddressViewModel.hasLocationAccess();
    }

    private void createAndShowRequestPermissionRationalDialog() {
        Log.d(TAG,"createAndShowRequestPermissionRationalDialog ");

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
                    getLocation();
                }else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                        Log.d(TAG,"shouldShowRequestPermissionRationale ");

                        createAndShowRequestPermissionRationalDialog();
                    }
                }
                return;
        }
    }

    private void getLocation() {
        if (!mAddAddressViewModel.isLocationEnable()) {
            Log.d(TAG, "isLocationEnable is false ");

            createAndShowChangeLocationSettingDialog();
        } else {
            requestLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestLocation() {

        Log.d(TAG,"AddADressFragment + requestLocation  ");
        Log.d(TAG,"hasLocationAccess is   "+hasLocationAccess());

        if (!hasLocationAccess())
            return;

        mAddAddressViewModel.requestLocation();

    }

    private void updateUI() {
        Log.d(TAG,"AddAddressFragment + updateUI ");


        Location userLocation = mAddAddressViewModel.getUserLocation().getValue();
        Log.d(TAG,"AddAddressFragment + userLocation is  null is  "+ (userLocation==null));
        Log.d(TAG,"AddAddressFragment + mGoogleMap is null is "+ (mGoogleMap==null));

        if (userLocation == null || mGoogleMap == null)
            return;

        LatLng userLatLng = new LatLng(userLocation.getLatitude(),
                userLocation.getLongitude());

        createAndAddMarker(userLatLng);
        animateCamera(mAddAddressViewModel.getLatLngBounds(userLatLng));
        mAddAddressViewModel.setUnregisteredAddress(userLatLng);

        setMapListener();
    }


    private void setMapListener() {
        Log.d(TAG,"AddAddressFragment + setMapListener ");

        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                if (mMarker != null) {
                    mMarker.remove();
                }
                createAndAddMarker(point);
                animateCamera(mAddAddressViewModel.getLatLngBounds(point));
               }
        });
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mAddAddressViewModel.setUnregisteredAddress(marker.getPosition());
                mNavController.navigate(R.id.action_AddAddressFragment_to_AddressDetailFragment);

                return false;
            }
        });

    }


    private void animateCamera(LatLngBounds latLngBounds) {
        Log.d(TAG,"AddAddressFragment + animateCamera ");

        int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, margin);
        mGoogleMap.animateCamera(cameraUpdate);
    }

    private void createAndAddMarker(LatLng latLng) {
        Log.d(TAG,"AddAddressFragment + createAndAddMarker ");

        MarkerOptions userMarkerOptions = new MarkerOptions().
                position(latLng)
                .title("شما اینجا هستید");

        mMarker = mGoogleMap.addMarker(userMarkerOptions);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = Navigation.findNavController(view);
    }

}