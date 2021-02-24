package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
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

    private List<Category> mCategories = new ArrayList<>();
    private FragmentCategoriesBinding mBinding;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoriesViewModel = new ViewModelProvider(this).
                get(CategoriesViewModel.class);

        initData();

    }

    private void initData() {
        mCategoriesViewModel.setHealthProductsLiveData();
        mCategoriesViewModel.setDigitalSubCategoriesLiveData();
        mCategoriesViewModel.setSupermarketSubCategoriesLiveData();
        mCategoriesViewModel.setSpecialSaleProductsLiveData();
        mCategoriesViewModel.setBookAndArtSubCategoriesLiveData();
        mCategoriesViewModel.mFashionAndClothingSubCategoriesLiveData();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_categories,
                container,
                false);
        initAllRecyclerViews();
        setObservers();
        return mBinding.getRoot();
    }


    private void initViews(List<Category> categories) {
        mCategories = categories;
        setTextName(categories);
    }


    private void setObservers() {
        mCategoriesViewModel.getParentCategoriesLiveData()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        initViews(categories);
                    }
                });
        mCategoriesViewModel.getHealthProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        setupProductsAdapter(mPAdapterHealth,
                                mBinding.recyclerViewCategoryOneCategoriesFragment, products);
                    }
                });

        mCategoriesViewModel.getDigitalSubCategoriesLiveData()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        setupCategoriesAdapter(mAdapterDigital,
                                mBinding.recyclerViewCategoryTwoCategoriesFragment,
                                categories);
                    }
                });
        mCategoriesViewModel.getSupermarketSubCategoriesLiveData()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        setupCategoriesAdapter(mAdapterSupermarket,
                                mBinding.recyclerViewCategoryThreeCategoriesFragment,
                                categories);
                    }
                });
        mCategoriesViewModel.getSpecialSaleProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        setupProductsAdapter(mPAdapterSpecialSale,
                                mBinding.recyclerViewCategoryFourCategoriesFragment,
                                products);
                    }
                });
        mCategoriesViewModel.getBookAndArtSubCategoriesLiveData()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        setupCategoriesAdapter(mAdapterBookAndArt,
                                mBinding.recyclerViewCategoryFiveCategoriesFragment
                        ,categories);
                    }
                });
        mCategoriesViewModel.getFashionAndClothingSubCategoriesLiveData()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        setupCategoriesAdapter(mAdapterFashionAndClothing,
                                mBinding.recyclerViewCategorySixCategoriesFragment
                        ,categories);
                    }
                });
    }

    private void setupCategoriesAdapter(CategoriesFragmentAdapter adapter,
                                        RecyclerView recyclerView,
                                        List<Category> categories) {

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
        initRecyclerView(mBinding.recyclerViewCategoryOneCategoriesFragment);
        initRecyclerView(mBinding.recyclerViewCategoryTwoCategoriesFragment);
        initRecyclerView(mBinding.recyclerViewCategoryThreeCategoriesFragment);
        initRecyclerView(mBinding.recyclerViewCategoryFourCategoriesFragment);
        initRecyclerView(mBinding.recyclerViewCategoryFiveCategoriesFragment);
        initRecyclerView(mBinding.recyclerViewCategorySixCategoriesFragment);

    }

    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
    }

    private void setTextName(List<Category> categories) {
        mBinding.textViewCategoryNameOneCategoriesFragment.setText(categories.get(0).getName());
        mBinding.textViewCategoryNameTwoCategoriesFragment.setText(categories.get(1).getName());
        mBinding.textViewCategoryNameThreeCategoriesFragment.setText(categories.get(2).getName());
        mBinding.textViewCategoryNameFourCategoriesFragment.setText(categories.get(3).getName());
        mBinding.textViewCategoryNameFiveCategoriesFragment.setText(categories.get(4).getName());
        mBinding.textViewCategoryNameSixCategoriesFragment.setText(categories.get(5).getName());

    }


}
