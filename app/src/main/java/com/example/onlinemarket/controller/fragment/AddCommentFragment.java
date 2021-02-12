package com.example.onlinemarket.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.onlinemarket.R;
public class AddCommentFragment extends Fragment {


    public AddCommentFragment() {
        // Required empty public constructor
    }

    public static AddCommentFragment newInstance() {
        AddCommentFragment fragment = new AddCommentFragment();
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
        View  view=inflater.inflate(R.layout.fragment_add_comment, container,
                false);
        return view;
    }
}