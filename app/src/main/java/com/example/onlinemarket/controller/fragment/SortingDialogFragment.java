package com.example.onlinemarket.controller.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.onlinemarket.IOnBackPress;
import com.example.onlinemarket.R;
import com.google.android.material.radiobutton.MaterialRadioButton;


public class SortingDialogFragment extends DialogFragment implements IOnBackPress {

    private MaterialRadioButton mRadioButtonPopularest;
    private MaterialRadioButton mRadioButtonLatest;
    private MaterialRadioButton mRadioButtonLowToHighPrice;
    private MaterialRadioButton mRadioButtonHighToLowPrice;
    public static final int RESULT_CODE_ORDER_DIALOG_FRAGMENT = 20;
    public static final String EXTRA_ORDER_DIALOG_FRAGMENT = "com.example.onlinemarket.EXTRA_ORDERING_FRAGMENT";
    public static final String TAG = "OrderDialogFragment";
    private String mOrderBy = "";


    public SortingDialogFragment() {
        // Required empty public constructor
    }

    public static SortingDialogFragment newInstance() {
        SortingDialogFragment fragment = new SortingDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_sorting, null);

        findDialogViews(view);
        setListener();

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setPositiveButton("اعمال مرتب سازی",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult(mOrderBy);
                    }
                })
                .setNegativeButton("بازگشت", null)
                .setView(view)
                .create();

        return alertDialog;
    }

    private void findDialogViews(View view) {
        mRadioButtonPopularest = view.findViewById(R.id.radio_button_popularest);
        mRadioButtonLatest = view.findViewById(R.id.latest);
        mRadioButtonHighToLowPrice = view.findViewById(R.id.radio_button_high_to_low_price);
        mRadioButtonLowToHighPrice = view.findViewById(R.id.radio_button_low_to_high_price);
    }

    private void setListener() {
        mRadioButtonPopularest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderBy = "popularity";
            }
        });
        mRadioButtonLatest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderBy = "date";
            }
        });

        mRadioButtonLowToHighPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderBy = "price";
            }
        });
        mRadioButtonHighToLowPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderBy = "price_asc";
            }
        });
    }

    private void sendResult(String order_by) {
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = RESULT_CODE_ORDER_DIALOG_FRAGMENT;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ORDER_DIALOG_FRAGMENT, order_by);
        fragment.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

}