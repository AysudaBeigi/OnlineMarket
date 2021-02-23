package com.example.onlinemarket.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.FragmentSortingBinding;


public class SortingDialogFragment extends DialogFragment   {

    public static final int RESULT_CODE_ORDER_DIALOG_FRAGMENT = 20;
    public static final String EXTRA_ORDER_DIALOG_FRAGMENT = "com.example.onlinemarket.EXTRA_ORDERING_FRAGMENT";
    public static final String TAG = "OrderDialogFragment";
    private String mOrderBy = "";
    private FragmentSortingBinding mBinding;


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
       mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_sorting, null,
               false);

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
                .setView(mBinding.getRoot())
                .create();

        alertDialog.setContentView(mBinding.getRoot());

        return alertDialog;
    }


    private void setListener() {
        mBinding.radioButtonPopularest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderBy = "popularity";
            }
        });
        mBinding.radioButtonLatest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderBy = "date";
            }
        });

        mBinding.radioButtonLowToHighPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderBy = "price";
            }
        });
        mBinding.radioButtonHighToLowPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderBy = "price_asc";
            }
        });
    }

    private void sendResult(String orderBy) {
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = RESULT_CODE_ORDER_DIALOG_FRAGMENT;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ORDER_DIALOG_FRAGMENT, orderBy);
        fragment.onActivityResult(requestCode, resultCode, intent);
    }



}