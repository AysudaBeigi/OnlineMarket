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
import com.example.onlinemarket.utils.OnlineMarketPreferences;
import com.example.onlinemarket.viewModel.SubCategoryProductsViewModel;

import java.util.ArrayList;
import java.util.List;


public class SubCategoryProductsFragment extends Fragment {
    public static final String ARGS_SUBCATEGORY_ID = "argsSubcategoryId";
    private NavController mNavController;
    private FragmentSubCategoryProductsBinding mBinding;
    private SubCategoryProductsViewModel mSubCategoryProductsViewModel;
    private SubCategoryProductsAdapter mHealthProductsAdapter;
    private SubCategoryProductsAdapter mDigitalProductsAdapter;
    private SubCategoryProductsAdapter mSupermarketProductsAdapter;
    private SubCategoryProductsAdapter mSpecialSaleProductsAdapter;
    private SubCategoryProductsAdapter mBookAndArtsProductsAdapter;
    private SubCategoryProductsAdapter mFashionAndClothingProductsAdapter;
    private ArrayList<SubCategoryProductsAdapter> mSubCategoryProductsAdapters = new ArrayList<>();


    public SubCategoryProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubCategoryProductsViewModel = new ViewModelProvider(this)
                .get(SubCategoryProductsViewModel.class);

        mSubCategoryProductsAdapters.add(mHealthProductsAdapter);
        mSubCategoryProductsAdapters.add(mDigitalProductsAdapter);
        mSubCategoryProductsAdapters.add(mSupermarketProductsAdapter);
        mSubCategoryProductsAdapters.add(mSpecialSaleProductsAdapter);
        mSubCategoryProductsAdapters.add(mBookAndArtsProductsAdapter);
        mSubCategoryProductsAdapters.add(mFashionAndClothingProductsAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_sub_category_products,
                container, false);
        initViews();
        setObservers();
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
        mNavController = Navigation.findNavController(view);
    }

    private void replaceSearchResultFragment(String query) {

        OnlineMarketPreferences.getInstance(getActivity())
                .setQueryMap(NetworkParams.getSearchCategoryProducts(query,
                        mSubCategoryProductsViewModel.getUserSelectedCategory().getId()));
        mSubCategoryProductsViewModel.
                setSearchResultProductsLiveData(NetworkParams.getSearchCategoryProducts(query,
                        mSubCategoryProductsViewModel.getUserSelectedCategory().getId()));

        mNavController.navigate(
                R.id.action_SubCategoryProductsFragment_to_SearchResultFragment);

    }


    private void initViews() {
        mBinding.recyclerViewFragmentSubCategoryProducts.
                setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void setObservers() {

        mSubCategoryProductsViewModel.getCategoryProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        getSelectedCategoryAdapter(products);
                    }
                });
    }

    private void getSelectedCategoryAdapter(List<Product> products) {

        int userSelectedAdapterId = mSubCategoryProductsViewModel
                .getUserSelectedCategory().getId();
        setupAdapter(products, mSubCategoryProductsAdapters.get(userSelectedAdapterId));
    }

    private void setupAdapter(List<Product> products, SubCategoryProductsAdapter adapter) {
        if (adapter == null) {
            adapter =
                    new SubCategoryProductsAdapter(getActivity(), products, this);
            mBinding.recyclerViewFragmentSubCategoryProducts.
                    setAdapter(adapter);
        } else {
            adapter.setProducts(products);
        }
    }


}