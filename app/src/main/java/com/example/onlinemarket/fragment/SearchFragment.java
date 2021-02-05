package com.example.onlinemarket.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinemarket.IOnBackPress;
import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ProductsVerticalAdapter;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.MarketRepository;

import java.util.List;

public class SearchFragment extends Fragment implements IOnBackPress {

    public static final String ARGS_QUERY = "ARGS_QUERY";

    private String mQuery = "";

    private RecyclerView mRecyclerView;
    private ProductsVerticalAdapter mAdapter;
    private MarketRepository mMarketRepository;


    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String query) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_QUERY, query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMarketRepository = new MarketRepository(getContext());
        mQuery = (String) getArguments().get(ARGS_QUERY);

        mMarketRepository.fetchSearchProducts(mQuery, new MarketRepository.productsCallback() {
            @Override
            public void onItemResponse(List<Product> items) {
                if (mAdapter == null) {
                    mAdapter = new ProductsVerticalAdapter(getContext(), items);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.setProductsItem(items);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        findViews(view);

        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.search_recycler_view);
    }


    @Override
    public boolean onBackPressed() {
        return true;
    }
}