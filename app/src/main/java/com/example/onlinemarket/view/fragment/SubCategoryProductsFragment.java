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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.SubCategoryProductsAdapter;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.repository.ProductRepository;
import com.example.onlinemarket.databinding.FragmentSubCategoryProductsBinding;

import java.util.List;


public class SubCategoryProductsFragment extends Fragment   {
    public static final String ARGS_SUBCATEGORY_ID = "argsSubcategoryId";
    private ProductRepository mProductRepository;
    private int mSubCategoryId;
    private NavController mNavController;
    private FragmentSubCategoryProductsBinding mBinding;

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
        mProductRepository = new ProductRepository(getActivity());
        mSubCategoryId = getArguments().getInt(ARGS_SUBCATEGORY_ID);
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

        Bundle bundle=new Bundle();
        bundle.putString(SearchResultFragment.ARGS_QUERY,query);
        bundle.putInt(SearchResultFragment.ARGS_CATEGORY_ID,-mSubCategoryId);
        mNavController.navigate(
                R.id.action_SubCategoryProductsFragment_to_SearchResultFragment
        ,bundle);

    }


    private void initViews() {
        mBinding.recyclerViewFragmentSubCategoryProducts.
                setLayoutManager(new LinearLayoutManager(getContext()));
        mProductRepository.setCategoryProductsLiveData(
                mSubCategoryId,
                new ProductRepository.productsCallback() {
                    @Override
                    public void onItemResponse(List<Product> products) {
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