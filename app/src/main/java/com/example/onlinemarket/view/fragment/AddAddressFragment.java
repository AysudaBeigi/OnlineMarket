package com.example.onlinemarket.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlinemarket.R;
import com.example.onlinemarket.viewModel.AddAddressViewModel;

public class AddAddressFragment extends Fragment {


    public static final int REQUEST_CODE_PERMISSION_LOCATION = 0;
    private AddAddressViewModel mAddAddressViewModel;


    public AddAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddAddressViewModel = new ViewModelProvider(this).
                get(AddAddressViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_address,
                container, false);
        if (hasLocationAccess()) {
            if (!mAddAddressViewModel.isLocationEnable()) {
                createAndShowChangeLocationSettingDialog();
            } else {
                requestLocation();
                //todo: get the map
            }
        } else {
            requestLocationAccessPermission();
        }
        return view;
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
}