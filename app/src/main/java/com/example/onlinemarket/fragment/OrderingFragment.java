package com.example.onlinemarket.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.onlinemarket.R;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.MarketRepository;

import java.util.List;


public class OrderingFragment extends Fragment {

    private RadioButton mPopular;
    private RadioButton mNewest;
    private RadioButton mLowToHigh;
    private RadioButton mHighToLow;
    private MarketRepository mMarketRepository;
    private String mOrderBy = "";
    public static final String ARGS_CATEGORY_ID = "argsCategoryId";
    private int mCategoryId;


    public OrderingFragment() {
        // Required empty public constructor
    }


    public static OrderingFragment newInstance(int categoryId) {
        OrderingFragment fragment = new OrderingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putInt(ARGS_CATEGORY_ID,categoryId);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMarketRepository= new MarketRepository(getActivity());
        mCategoryId=getArguments().getInt(ARGS_CATEGORY_ID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ordering, container,
                false);
        findViews(view);
        setListeners();
        return view;
    }


    private void findViews(View view) {
        mPopular = view.findViewById(R.id.popularity);
        mNewest = view.findViewById(R.id.latest);
        mHighToLow = view.findViewById(R.id.high_to_low);
        mLowToHigh = view.findViewById(R.id.low_to_high);
    }

    private void setListeners() {
        mPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderBy = "popularity";
            }
        });
        mNewest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderBy = "date";
            }
        });

        mLowToHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderBy = "price";
            }
        });
        mHighToLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderBy = "price-desc";
            }
        });
        if(mCategoryId==-1){
            mMarketRepository.fetchProductsByOrder(1, mOrderBy, new MarketRepository.productsCallback() {
                @Override
                public void onItemResponse(List<Product> products) {

                }
            });
        }else {
            mMarketRepository.fetchCategoryProductByOrder(1, mCategoryId, mOrderBy,
                    new MarketRepository.productsCallback() {
                        @Override
                        public void onItemResponse(List<Product> items) {

                        }
                    });
        }
    }

}