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
import com.example.onlinemarket.data.remote.NetworkParams;
import com.example.onlinemarket.data.repository.ProductRepository;
import com.example.onlinemarket.databinding.FragmentSearchResultBinding;
import com.example.onlinemarket.viewModel.SearchResultViewModel;

import java.util.List;
import java.util.Map;

public class SearchResultFragment extends Fragment   {

    public static final String ARGS_QUERY = "ARGS_QUERY";
    public static final String ARGS_CATEGORY_ID = "argsCategoryId";
    public static final int REQUEST_CODE_ORDER = 10;
    public static final String TAG_CHOOSE_ORDER = "TAG_CHOOSE_ORDER";

    private String mQuery = "";

    private SearchResultProductsAdapter mAdapter;
    private ProductRepository mProductRepository;
    private int mCategoryId;
    Map<String, String> mSearchQueryMap;
    private FragmentSearchResultBinding mBinding;
    private SearchResultViewModel  mSearchResultViewModel;

    public SearchResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProductRepository = new ProductRepository(getContext());
        mSearchResultViewModel =new ViewModelProvider(this).
                get(SearchResultViewModel.class);

        searchAndInitViews();

    }

    private void searchAndInitViews() {
        mSearchResultViewModel.getSearchResultProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        if (mAdapter == null) {
                            mAdapter = new SearchResultProductsAdapter(getContext(), products);
                            mBinding.recyclerViewSearchResult.setAdapter(mAdapter);
                        } else {
                            mAdapter.setProductsItem(products);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       mBinding= DataBindingUtil.inflate(inflater,
               R.layout.fragment_search_result, container, false);
        setListeners();
        return mBinding.getRoot();
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

            String order =
                    (String) data.getSerializableExtra(SortingDialogFragment.
                            EXTRA_ORDER_DIALOG_FRAGMENT);
            if (mCategoryId == -1) {
                mSearchQueryMap = NetworkParams.
                        getOrderedSearchAllProducts(mQuery, order);
            } else {
                mSearchQueryMap = NetworkParams.
                        getOrderedSearchCategoryProducts(mQuery, mCategoryId, order);
            }
            searchAndInitViews();

        }

    }

}