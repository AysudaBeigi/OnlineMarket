package com.example.onlinemarket.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.example.onlinemarket.R;
import com.google.android.material.textfield.TextInputEditText;

public class AddCommentFragment extends Fragment {
    private TextInputEditText mEditTextComment;
    private RadioGroup mRadioGroupRating;
    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;
    private RadioButton mRadioButton3;
    private RadioButton mRadioButton4;
    private RadioButton mRadioButton5;

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
        mRadioGroupRating=view.findViewById(R.id.radio_group_rating);
        mRadioButton1=view.findViewById(R.id.radio_button_1);

        mRadioGroupRating.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==mRadioButton1.getId()){
                    mRadioButton1.setChecked(true);
                }

            }
        });
        return view;
    }




}