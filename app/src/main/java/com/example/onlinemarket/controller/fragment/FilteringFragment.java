package com.example.onlinemarket.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.onlinemarket.R;
import com.example.onlinemarket.model.Attribute;
import com.example.onlinemarket.repository.MarketRepository;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class FilteringFragment extends Fragment {
    public static final String ARGS_CATEGORY_ID = "argsCategoryId";
    private int mCategoryId;
    private MarketRepository mMarketRepository;
    private MaterialTextView mTextView1, mTextView2, mTextView3, mTextView4, mTextView5, mTextView6;
    List<Attribute> mAttributes;

    public FilteringFragment() {
        // Required empty public constructor
    }


    public static FilteringFragment newInstance() {
        FilteringFragment fragment = new FilteringFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMarketRepository = new MarketRepository(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filttering, container,
                false);
        findViews(view);

       /* mMarketRepository.fetchAttributes(
                new MarketRepository.AttributesCallback() {
                    @Override
                    public void onItemResponse(List<Attribute> attributes) {
                        mAttributes = attributes;
                    }
                });
        ArrayList<TextView> textViews = new ArrayList<>();
        textViews.add(mTextView1);
        textViews.add(mTextView2);
        textViews.add(mTextView3);
        textViews.add(mTextView4);
        textViews.add(mTextView5);
        textViews.add(mTextView6);
        for (int i = 0; i < mAttributes.size(); i++) {
            textViews.get(i).setText(mAttributes.get(i).getName());
        }*/


        return view;
    }

    private void findViews(View view) {
        mTextView1 = view.findViewById(R.id.text_view_filter_one);
        mTextView2 = view.findViewById(R.id.text_view_filter_two);
        mTextView3 = view.findViewById(R.id.text_view_filter_three);
        mTextView4 = view.findViewById(R.id.text_view_filter_four);
        mTextView5 = view.findViewById(R.id.text_view_filter_five);
        mTextView6 = view.findViewById(R.id.text_view_filter_six);
    }
}