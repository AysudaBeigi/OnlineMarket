package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.SubCategoryProductsAdapter;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.remote.NetworkParams;
import com.example.onlinemarket.databinding.FragmentSubCategoryProductsBinding;
import com.example.onlinemarket.viewModel.SearchResultViewModel;
import com.example.onlinemarket.viewModel.SubCategoryProductsViewModel;

import java.util.List;


public class SubCategoryProductsFragment extends Fragment   {
    public static final String ARGS_SUBCATEGORY_ID = "argsSubcategoryId";
    private int mSubCategoryId;
    private NavController mNavController;
    private FragmentSubCategoryProductsBinding mBinding;
    private SubCategoryProductsViewModel mSubCategoryProductsViewModel;


    public SubCategoryProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubCategoryProductsViewModel= new ViewModelProvider(this)
                        .get(SubCategoryProductsViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate(inflater,
                R.layout.fragment_sub_category_products,
                container, false);
        initViews();
        setListeners();
        return mBinding.getRoot();
    }
    private void setListeners() {
        mBinding.searchViewSubCategoryProducts.
                setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                replaceSearchResultFragment(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController=Navigation.findNavController(view);
    }

    private void replaceSearchResultFragment(String query) {

        SearchResultViewModel searchResultViewModel=new ViewModelProvider(this)
                .get(SearchResultViewModel.class);
        searchResultViewModel.
                setSearchResultProductsLiveData(NetworkParams.getSearchCategoryProducts(query,
                        mSubCategoryId));
        mNavController.navigate(
                R.id.action_SubCategoryProductsFragment_to_SearchResultFragment);

    }


    private void initViews() {
        mBinding.recyclerViewFragmentSubCategoryProducts.
                setLayoutManager(new LinearLayoutManager(getContext()));
        mSubCategoryProductsViewModel.getCategoryProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        initAdapter(products);
                    }
                });


    }

    private void initAdapter(List<Product> categoryProduct) {
        SubCategoryProductsAdapter productVerticalAdapter =
                new SubCategoryProductsAdapter(getActivity(), categoryProduct);
        mBinding.recyclerViewFragmentSubCategoryProducts.setAdapter(productVerticalAdapter);
    }



}