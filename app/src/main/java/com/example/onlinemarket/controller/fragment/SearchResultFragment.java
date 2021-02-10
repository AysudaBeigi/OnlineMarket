package com.example.onlinemarket.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.IOnBackPress;
import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ProductsVerticalAdapter;
import com.example.onlinemarket.model.product.Product;
import com.example.onlinemarket.network.NetworkParams;
import com.example.onlinemarket.repository.MarketRepository;

import java.util.List;
import java.util.Map;

public class SearchResultFragment extends Fragment implements IOnBackPress {

    public static final String ARGS_QUERY = "ARGS_QUERY";
    public static final String ARGS_CATEGORY_ID = "argsCategoryId";
    public static final int REQUEST_CODE_ORDER = 10;
    public static final String TAG_CHOOSE_ORDER = "TAG_CHOOSE_ORDER";

    private String mQuery = "";

    private RecyclerView mRecyclerView;
    private ProductsVerticalAdapter mAdapter;
    private MarketRepository mMarketRepository;
    private ImageView mSort, mFilter;
    private int mCategoryId;
    Map<String, String> mSearchQueryMap;

    public SearchResultFragment() {
        // Required empty public constructor
    }

    public static SearchResultFragment newInstance(String query, int categoryId) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_QUERY, query);
        args.putInt(ARGS_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMarketRepository = new MarketRepository(getContext());
        mQuery = (String) getArguments().get(ARGS_QUERY);
        mCategoryId = getArguments().getInt(ARGS_CATEGORY_ID);
        if (mCategoryId == -1) {
            mSearchQueryMap = NetworkParams.getSearchAllProducts(mQuery);
        } else {
            mSearchQueryMap = NetworkParams.getSearchCategoryProducts(mQuery, mCategoryId);
        }

        searchAndInitViews();


    }

    private void searchAndInitViews() {
        mMarketRepository.fetchSearchProducts(mSearchQueryMap,
                new MarketRepository.productsCallback() {
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
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        findViews(view);
        setListeners();
        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_search_result);
        mSort = view.findViewById(R.id.sort_order);
        mFilter = view.findViewById(R.id.filter_product);

    }

    private void setListeners() {
        mSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortingFragment orderDialogFragment = SortingFragment.newInstance();
                orderDialogFragment.setTargetFragment(
                        SearchResultFragment.this, REQUEST_CODE_ORDER);
                orderDialogFragment.show(getParentFragmentManager(), TAG_CHOOSE_ORDER);
            }
        });
        mFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*getActivity().
                        getSupportFragmentManager().
                        beginTransaction()
                        .replace(R.id.fragment_container_main_activity,
                                FilteringFragment.
                                        newInstance())
                        .commit();
*/
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data == null)
            return;
        if (requestCode == REQUEST_CODE_ORDER
                && resultCode == SortingFragment.RESULT_CODE_ORDER_DIALOG_FRAGMENT) {

            String order =
                    (String) data.getSerializableExtra(SortingFragment.
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


    @Override
    public boolean onBackPressed() {
        return true;
    }
}