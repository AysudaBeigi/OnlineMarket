package com.example.onlinemarket.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ProductVerticalAdapter;
import com.example.onlinemarket.model.product.Product;
import com.example.onlinemarket.repository.MarketRepository;

import java.util.List;


public class SubCategoryProductsFragment extends Fragment   {
    public static final String ARGS_SUBCATEGORY_ID = "argsSubcategoryId";
    private RecyclerView mRecyclerViewSubCategoryProducts;
    private MarketRepository mMarketRepository;
    private int mSubCategoryId;
    private SearchView mSearchViewSubCategoryProducts;
    public SubCategoryProductsFragment() {
        // Required empty public constructor
    }


    public static SubCategoryProductsFragment newInstance() {
        SubCategoryProductsFragment fragment = new SubCategoryProductsFragment();
        Bundle args = new Bundle();
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
        setListeners(view);
        return view;
    }
    private void setListeners(View rootLayout) {
        mSearchViewSubCategoryProducts.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                replaceSearchResultFragment(query,rootLayout);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    private void replaceSearchResultFragment(String query,View view) {

        NavController navController= Navigation.findNavController(view);
        Bundle bundle=new Bundle();
        bundle.putString(SearchResultFragment.ARGS_QUERY,query);
        bundle.putInt(SearchResultFragment.ARGS_CATEGORY_ID,-mSubCategoryId);
        navController.navigate(
                R.id.action_SubCategoryProductsFragment_to_SearchResultFragment
        ,bundle);

    }


    private void findViews(View view) {
        mRecyclerViewSubCategoryProducts = view.
                findViewById(R.id.recycler_view_fragment_sub_category_products);
        mSearchViewSubCategoryProducts=view.findViewById(R.id.search_view);
    }

    private void initViews() {
        mRecyclerViewSubCategoryProducts.
                setLayoutManager(new LinearLayoutManager(getContext()));
        mMarketRepository.fetchCategoryProduct(
                mSubCategoryId,
                new MarketRepository.productsCallback() {
                    @Override
                    public void onItemResponse(List<Product> products) {
                        initAdapter(products);
                    }
                });



    }

    private void initAdapter(List<Product> categoryProduct) {
        ProductVerticalAdapter productVerticalAdapter =
                new ProductVerticalAdapter(getActivity(), categoryProduct);
        mRecyclerViewSubCategoryProducts.setAdapter(productVerticalAdapter);
    }



}