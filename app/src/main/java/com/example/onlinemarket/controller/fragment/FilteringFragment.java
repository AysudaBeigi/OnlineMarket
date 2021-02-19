package com.example.onlinemarket.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.FragmentFiltteringBinding;
import com.example.onlinemarket.model.Attribute;
import com.example.onlinemarket.repository.MarketRepository;

import java.util.List;

public class FilteringFragment extends Fragment {
    public static final String ARGS_CATEGORY_ID = "argsCategoryId";
    private int mCategoryId;
    private MarketRepository mMarketRepository;
    List<Attribute> mAttributes;
    private FragmentFiltteringBinding mBinding;

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
       mBinding= DataBindingUtil.
               inflate(inflater, R.layout.fragment_filttering,
                       container, false);

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


        return mBinding.getRoot();
    }


}