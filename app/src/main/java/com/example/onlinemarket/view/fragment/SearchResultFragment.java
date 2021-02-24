package com.example.onlinemarket.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.SearchResultProductsAdapter;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.databinding.FragmentSearchResultBinding;
import com.example.onlinemarket.viewModel.SearchResultViewModel;

import java.util.List;

public class SearchResultFragment extends Fragment {

    public static final int REQUEST_CODE_ORDER = 10;
    public static final String TAG_CHOOSE_ORDER = "TAG_CHOOSE_ORDER";
    private SearchResultProductsAdapter mAdapter;
    private FragmentSearchResultBinding mBinding;
    private SearchResultViewModel mSearchResultViewModel;

    public SearchResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSearchResultViewModel = new ViewModelProvider(this).
                get(SearchResultViewModel.class);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_search_result, container, false);
        setObservers();
        setListeners();
        return mBinding.getRoot();
    }

    private void setObservers() {
        mSearchResultViewModel.getSearchResultProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        setupAdapter(products);
                    }
                });
    }

    private void setupAdapter(List<Product> products) {

            mAdapter = new SearchResultProductsAdapter(getContext(), products,this);
            mBinding.recyclerViewSearchResult.setAdapter(mAdapter);

    }



    private void setListeners() {
        mBinding.imageViewSortSearchResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SortingDialogFragment orderDialogFragment = SortingDialogFragment.newInstance();
                orderDialogFragment.setTargetFragment(
                        SearchResultFragment.this, REQUEST_CODE_ORDER);
                orderDialogFragment.show(getParentFragmentManager(), TAG_CHOOSE_ORDER);
            }
        });
        mBinding.imageViewFilterSearchResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo : handle filter section
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data == null)
            return;
        if (requestCode == REQUEST_CODE_ORDER
                && resultCode == SortingDialogFragment.RESULT_CODE_ORDER_DIALOG_FRAGMENT) {

            String orderBy =
                    (String) data.getSerializableExtra(SortingDialogFragment.
                            EXTRA_ORDER_DIALOG_FRAGMENT);
            mSearchResultViewModel.setOrderedSearchResultProductsLiveData(orderBy);
            setObservers();

        }

    }

}