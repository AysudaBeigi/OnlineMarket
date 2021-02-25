package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.CategoriesFragmentAdapter;
import com.example.onlinemarket.adapter.CategoryProductsHorizontalAdapter;
import com.example.onlinemarket.data.model.product.Category;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.databinding.FragmentCategoriesBinding;
import com.example.onlinemarket.viewModel.CategoriesViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {
    public static String TAG = "OnlineMarket";

    private CategoriesFragmentAdapter mAdapterDigital;
    private CategoriesFragmentAdapter mAdapterSupermarket;
    private CategoriesFragmentAdapter mAdapterBookAndArt;
    private CategoriesFragmentAdapter mAdapterFashionAndClothing;

    private CategoryProductsHorizontalAdapter mPAdapterHealth;
    private CategoryProductsHorizontalAdapter mPAdapterSpecialSale;

    private CategoriesViewModel mCategoriesViewModel;

    private FragmentCategoriesBinding mBinding;
    private List<Category> mParents;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "CategoriesFragment : onCreate");

        mCategoriesViewModel = new ViewModelProvider(this).
                get(CategoriesViewModel.class);


    }

    private void initData() {

        Log.d(TAG, "CategoriesFragment : initData");

        mCategoriesViewModel.setHealthProductsLiveData(mParents.get(0).getId());
        mCategoriesViewModel.setDigitalSubCategoriesLiveData(mParents.get(1).getId());
        mCategoriesViewModel.setSupermarketSubCategoriesLiveData(mParents.get(2).getId());
        mCategoriesViewModel.setSpecialSaleProductsLiveData(mParents.get(3).getId());
        mCategoriesViewModel.setBookAndArtSubCategoriesLiveData(mParents.get(4).getId());
        mCategoriesViewModel.mFashionAndClothingSubCategoriesLiveData(mParents.get(5).getId());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "CategoriesFragment : onCreateView");

        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_categories,
                container,
                false);
        initAllRecyclerViews();
        setObservers();
        return mBinding.getRoot();
    }


    private void initViews(List<Category> categories) {
        Log.d(TAG, "CategoriesFragment : initViews");
        mParents = new ArrayList<>();
        mParents = categories;
        setTextName(categories);
    }


    private void setObservers() {
        Log.d(TAG, "CategoriesFragment : setObservers");

        mCategoriesViewModel.getParentCategoriesLiveData()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        Log.d(TAG, "getParentCategoriesLiveData : onChanged");
                        Log.d(TAG, "size is " + categories.size());

                        initViews(categories);
                        initData();
                    }
                });
        mCategoriesViewModel.getHealthProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        Log.d(TAG, "getHealthProductsLiveData : onChanged");
                        Log.d(TAG, "size is " + products.size());

                        setupProductsAdapter(mPAdapterHealth,
                                mBinding.recyclerViewCategoryOneCategoriesFragment, products);
                    }
                });

        mCategoriesViewModel.getDigitalSubCategoriesLiveData()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        Log.d(TAG, "getDigitalSubCategoriesLiveData : onChanged");
                        Log.d(TAG, "size is " + categories.size());

                        setupCategoriesAdapter(mAdapterDigital,
                                mBinding.recyclerViewCategoryTwoCategoriesFragment,
                                categories);
                    }
                });
        mCategoriesViewModel.getSupermarketSubCategoriesLiveData()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        Log.d(TAG, "getSupermarketSubCategoriesLiveData : onChanged");
                        Log.d(TAG, "size is " + categories.size());

                        setupCategoriesAdapter(mAdapterSupermarket,
                                mBinding.recyclerViewCategoryThreeCategoriesFragment,
                                categories);
                        //mAdapterSupermarket.notifyDataSetChanged();
                    }
                });
        mCategoriesViewModel.getSpecialSaleProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        Log.d(TAG, "getSpecialSaleProductsLiveData : onChanged");
                        Log.d(TAG, "size is " + products.size());

                        setupProductsAdapter(mPAdapterSpecialSale,
                                mBinding.recyclerViewCategoryFourCategoriesFragment,
                                products);
                    }
                });
        mCategoriesViewModel.getBookAndArtSubCategoriesLiveData()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        Log.d(TAG, "getBookAndArtSubCategoriesLiveData : onChanged");
                        Log.d(TAG, "size is " + categories.size());

                        setupCategoriesAdapter(mAdapterBookAndArt,
                                mBinding.recyclerViewCategoryFiveCategoriesFragment
                                , categories);
                    }
                });
        mCategoriesViewModel.getFashionAndClothingSubCategoriesLiveData()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        Log.d(TAG, "getFashionAndClothingSubCategoriesLiveData : onChanged");
                        Log.d(TAG, "size is " + categories.size());

                        setupCategoriesAdapter(mAdapterFashionAndClothing,
                                mBinding.recyclerViewCategorySixCategoriesFragment
                                , categories);
                    }
                });
    }

    private void setupCategoriesAdapter(CategoriesFragmentAdapter adapter,
                                        RecyclerView recyclerView,
                                        List<Category> categories) {
        Log.d(TAG, "setupCategoriesAdapter ");

        if (adapter == null) {
            adapter = new CategoriesFragmentAdapter(getContext(),
                    categories, this);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setCategories(categories);
            adapter.notifyDataSetChanged();
        }
    }

    private void setupProductsAdapter(CategoryProductsHorizontalAdapter adapter,
                                      RecyclerView recyclerView,
                                      List<Product> products) {
        Log.d(TAG, "setupProductsAdapter ");

        if (adapter == null) {
            adapter = new CategoryProductsHorizontalAdapter
                    (getContext(), products, this);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setProducts(products);
            adapter.notifyDataSetChanged();
        }
    }

    public void initAllRecyclerViews() {
        Log.d(TAG, "initAllRecyclerViews ");

        initRecyclerView(mBinding.recyclerViewCategoryOneCategoriesFragment);
        initRecyclerView(mBinding.recyclerViewCategoryTwoCategoriesFragment);
        initRecyclerView(mBinding.recyclerViewCategoryThreeCategoriesFragment);
        initRecyclerView(mBinding.recyclerViewCategoryFourCategoriesFragment);
        initRecyclerView(mBinding.recyclerViewCategoryFiveCategoriesFragment);
        initRecyclerView(mBinding.recyclerViewCategorySixCategoriesFragment);

    }

    private void initRecyclerView(RecyclerView recyclerView) {
        Log.d(TAG, "initRecyclerView ");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
    }

    private void setTextName(List<Category> categories) {
        Log.d(TAG, "setTextName ");

        mBinding.setCategories(categories);
       /* mBinding.textViewCategoryNameOneCategoriesFragment.setText(categories.get(0).getName());
        mBinding.textViewCategoryNameTwoCategoriesFragment.setText(categories.get(1).getName());
        mBinding.textViewCategoryNameThreeCategoriesFragment.setText(categories.get(2).getName());
        mBinding.textViewCategoryNameFourCategoriesFragment.setText(categories.get(3).getName());
        mBinding.textViewCategoryNameFiveCategoriesFragment.setText(categories.get(4).getName());
        mBinding.textViewCategoryNameSixCategoriesFragment.setText(categories.get(5).getName());
*/
    }


}
