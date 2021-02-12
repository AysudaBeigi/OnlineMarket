package com.example.onlinemarket.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.onlinemarket.R;
public class AddcommentFragment extends Fragment {


    public AddcommentFragment() {
        // Required empty public constructor
    }

    public static AddcommentFragment newInstance() {
        AddcommentFragment fragment = new AddcommentFragment();
        Bundle args = new Bundle();
           fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view=inflater.inflate(R.layout.fragment_addcomment, container,
                false);
        return view;
    }
}