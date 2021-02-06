package com.example.onlinemarket.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinemarket.R;

public class FilteringFragment extends Fragment {
    public static final String ARGS_CATEGORY_ID = "argsCategoryId";
    private int mCategoryId;



    public FilteringFragment() {
        // Required empty public constructor
    }


    public static FilteringFragment newInstance(int categoryId) {
        FilteringFragment fragment = new FilteringFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putInt(ARGS_CATEGORY_ID,categoryId);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryId=getArguments().getInt(ARGS_CATEGORY_ID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_filtering, container,
                false);
        return  view;
    }
}