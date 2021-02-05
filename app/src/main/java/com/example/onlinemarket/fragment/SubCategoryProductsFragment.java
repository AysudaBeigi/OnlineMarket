package com.example.onlinemarket.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinemarket.IOnBackPress;
import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.CategoriesAdapter;
import com.example.onlinemarket.adapter.ProductsHorizontalAdapter;
import com.example.onlinemarket.adapter.ProductsVerticalAdapter;
import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.MarketRepository;

import java.util.ArrayList;
import java.util.List;


public class SubCategoryProductsFragment extends Fragment implements IOnBackPress {
    public static final String ARGS_SUBCATEGORY_ID = "argsSubcategoryId";
    private RecyclerView mRecyclerViewSubCategoryProducts;
    private MarketRepository mMarketRepository;

    private int mSubCategoryId;

    public SubCategoryProductsFragment() {
        // Required empty public constructor
    }


    public static SubCategoryProductsFragment newInstance(int subCategoryId) {
        SubCategoryProductsFragment fragment = new SubCategoryProductsFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_SUBCATEGORY_ID, subCategoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMarketRepository = new MarketRepository(getActivity());
        mSubCategoryId = getArguments().getInt(ARGS_SUBCATEGORY_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_category_products,
                container, false);
        findViews(view);
        initViews();
        return view;
    }

    private void findViews(View view) {
        mRecyclerViewSubCategoryProducts = view.
                findViewById(R.id.fragment_sub_category_products_recycler_view);
    }

    private void initViews() {
        mRecyclerViewSubCategoryProducts.
                setLayoutManager(new LinearLayoutManager(getContext()));
        mMarketRepository.fetchCategoryProduct(1,
                mSubCategoryId,
                new MarketRepository.productsCallback() {
                    @Override
                    public void onItemResponse(List<Product> products) {
                        initAdapter(products);
                    }
                });

    }

    private void initAdapter(List<Product> categoryProduct) {
        ProductsVerticalAdapter productsVerticalAdapter =
                new ProductsVerticalAdapter(getActivity(), categoryProduct);
        mRecyclerViewSubCategoryProducts.setAdapter(productsVerticalAdapter);
    }


    @Override
    public boolean onBackPressed() {
        return true;
    }


}