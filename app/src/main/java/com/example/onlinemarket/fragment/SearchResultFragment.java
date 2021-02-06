package com.example.onlinemarket.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.onlinemarket.IOnBackPress;
import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ProductsVerticalAdapter;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.MarketRepository;

import java.util.List;

public class SearchResultFragment extends Fragment implements IOnBackPress {

    public static final String ARGS_QUERY = "ARGS_QUERY";
    public static final String ARGS_CATEGORY_ID = "argsCategoryId";

    private String mQuery = "";

    private RecyclerView mRecyclerView;
    private ProductsVerticalAdapter mAdapter;
    private MarketRepository mMarketRepository;
    private ImageView mSort, mFilter;
    private int mCategoryId;
    private boolean mIsFilterOrSort=false;

    public SearchResultFragment() {
        // Required empty public constructor
    }

    public static SearchResultFragment newInstance(String query, int categoryId) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_QUERY, query);
        args.putInt(ARGS_CATEGORY_ID,categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMarketRepository = new MarketRepository(getContext());
        mQuery = (String) getArguments().get(ARGS_QUERY);
        mCategoryId=getArguments().getInt(ARGS_CATEGORY_ID);
        if(!mIsFilterOrSort){
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
                mIsFilterOrSort=true;
                getActivity().
                        getSupportFragmentManager().
                        beginTransaction()
                        .replace(R.id.fragment_container_main_activity,
                                OrderingFragment.
                                        newInstance(mCategoryId))
                        .commit();
            }
        });
        mFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsFilterOrSort=true;
                getActivity().
                        getSupportFragmentManager().
                        beginTransaction()
                        .replace(R.id.fragment_container_main_activity,
                                FilteringFragment.
                                        newInstance(mCategoryId))
                        .commit();

            }
        });

    }


    @Override
    public boolean onBackPressed() {
        return true;
    }
}