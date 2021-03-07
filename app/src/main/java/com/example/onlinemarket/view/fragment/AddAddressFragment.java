package com.example.onlinemarket.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
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

public class AddAddressFragment extends Fragment {


    public static final int REQUEST_CODE_PERMISSION_LOCATION = 0;
    private AddAddressViewModel mAddAddressViewModel;
    private GoogleMap mGoogleMap;
    private Marker mMarker;
    private NavController mNavController;
    private MaterialButton mButtonOkLocation;


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
        SupportMapFragment supportMapFragment =
                (SupportMapFragment) getActivity().getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_address, container,
                false);

        findViews(view);
        setListeners();
        return view;

    }

    private void setListeners() {
        mButtonOkLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Location userLocation = mAddAddressViewModel.getUserLocation().getValue();
                if (userLocation == null || mGoogleMap == null)
                    return;
                mNavController.navigate(R.id.action_AddAddressFragment_to_AddressDetailFragment);
            }
        });
    }

    private void findViews(View view) {
        mButtonOkLocation = view.findViewById(R.id.button_ok_location);
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
        animateCamera(mAddAddressViewModel.getLatLngBounds(userLatLng));
        mAddAddressViewModel.setUnregisteredAddress(userLatLng);
        setMapListener();
    }



    private void setMapListener() {
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                mAddAddressViewModel.setUnregisteredAddress(point);

                if (mMarker != null) {
                    mMarker.remove();
                }
                createAndAddMarker(point);
                animateCamera(mAddAddressViewModel.getLatLngBounds(point));
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
        mNavController = Navigation.findNavController(view);
    }

}