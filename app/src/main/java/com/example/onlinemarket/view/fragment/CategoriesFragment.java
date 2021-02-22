package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

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

    private CategoriesFragmentAdapter mAdapterOne;
    private CategoriesFragmentAdapter mAdapterTwo;
    private CategoriesFragmentAdapter mAdapterThree;
    private CategoriesFragmentAdapter mAdapterFour;
    private CategoriesFragmentAdapter mAdapterFive;
    private CategoriesFragmentAdapter mAdapterSix;

    private CategoryProductsHorizontalAdapter mPAdapterOne;
    private CategoryProductsHorizontalAdapter mPAdapterTwo;
    private CategoryProductsHorizontalAdapter mPAdapterThree;
    private CategoryProductsHorizontalAdapter mPAdapterFour;
    private CategoryProductsHorizontalAdapter mPAdapterFive;
    private CategoryProductsHorizontalAdapter mPAdapterSix;

    private CategoriesViewModel mCategoriesViewModel;

    private List<Category> mCategories = new ArrayList<>();
    private FragmentCategoriesBinding mBinding;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        mCategoriesViewModel.getCategoriesLiveData()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        initData(categories);
                        setupCategoriesRecyclerAdapter(CategoriesFragment.this,
                                CategoriesFragment.this);

                    }
                });

    }

    private void initData(List<Category> categories) {
        mCategories = categories;
        setTextName(categories);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_categories,
                container,
                false);

        return mBinding.getRoot();
    }


    private void setupCategoriesRecyclerAdapter(LifecycleOwner lifecycleOwner,
                                                ViewModelStoreOwner viewModelStoreOwner) {
        mCategoriesViewModel.
                setSubCategoriesLiveData(mCategories.get(0).getId());

        mCategoriesViewModel.getSubCategoriesLiveData()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        if (categories.size() > 0) {
                            if (mAdapterOne == null) {
                                mAdapterOne = new CategoriesFragmentAdapter(
                                        getContext(),
                                        categories, viewModelStoreOwner);
                                mBinding.recyclerViewCategoryOneCategoriesFragment
                                        .setAdapter(mAdapterOne);
                            } else {
                                mAdapterOne.setCategoriesItem(categories);
                            }

                        } else {
                            mCategoriesViewModel.
                                    setCategoryProductsLiveData(mCategories.get(0).getId());
                            mCategoriesViewModel.getCategoryProductsLiveData()
                                    .observe(lifecycleOwner, new Observer<List<Product>>() {
                                        @Override
                                        public void onChanged(List<Product> products) {
                                            if (mPAdapterOne == null) {
                                                mPAdapterOne = new CategoryProductsHorizontalAdapter(getContext(),
                                                        products);
                                                mBinding.recyclerViewCategoryOneCategoriesFragment
                                                        .setAdapter(mPAdapterOne);
                                            } else {
                                                mPAdapterOne.setProductsItem(products);
                                                mPAdapterOne.notifyDataSetChanged();
                                            }
                                        }
                                    });
                        }
                    }


                });

        mCategoriesViewModel.setSubCategoriesLiveData(mCategories.get(1).getId());
        mCategoriesViewModel.getSubCategoriesLiveData()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        if (categories.size() > 0) {
                            if (mAdapterTwo == null) {
                                mAdapterTwo = new CategoriesFragmentAdapter(getContext(),
                                        categories, viewModelStoreOwner);
                                mBinding.recyclerViewCategoryTwoCategoriesFragment
                                        .setAdapter(mAdapterTwo);
                            } else {
                                mAdapterTwo.setCategoriesItem(categories);
                                mAdapterTwo.notifyDataSetChanged();
                            }

                        } else {
                            mCategoriesViewModel.
                                    setCategoryProductsLiveData(mCategories.get(1).getId());
                            mCategoriesViewModel.getCategoryProductsLiveData()
                                    .observe(lifecycleOwner, new Observer<List<Product>>() {
                                        @Override
                                        public void onChanged(List<Product> products) {
                                            if (mPAdapterTwo == null) {
                                                mPAdapterTwo = new CategoryProductsHorizontalAdapter(
                                                        getContext(),
                                                        products);
                                                mBinding.recyclerViewCategoryTwoCategoriesFragment
                                                        .setAdapter(mPAdapterTwo);
                                            } else {
                                                mPAdapterTwo.setProductsItem(products);
                                                mPAdapterTwo.notifyDataSetChanged();
                                            }
                                        }
                                    });

                        }
                    }


                });

        mCategoriesViewModel.setSubCategoriesLiveData(mCategories.get(2).getId());
        mCategoriesViewModel.getSubCategoriesLiveData()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        if (categories.size() > 0) {

                            if (mAdapterThree == null) {
                                mAdapterThree = new CategoriesFragmentAdapter(getContext(),
                                        categories, viewModelStoreOwner);
                                mBinding.recyclerViewCategoryThreeCategoriesFragment
                                        .setAdapter(mAdapterThree);
                            } else {
                                mAdapterThree.setCategoriesItem(categories);
                                mAdapterThree.notifyDataSetChanged();
                            }

                        } else {
                            mCategoriesViewModel.
                                    setCategoryProductsLiveData(mCategories.get(2).getId());
                            mCategoriesViewModel.getCategoryProductsLiveData()
                                    .observe(lifecycleOwner, new Observer<List<Product>>() {
                                        @Override
                                        public void onChanged(List<Product> products) {
                                            if (mPAdapterThree == null) {
                                                mPAdapterThree = new CategoryProductsHorizontalAdapter(getContext(),
                                                        products);
                                                mBinding.recyclerViewCategoryThreeCategoriesFragment
                                                        .setAdapter(mPAdapterThree);
                                            } else {
                                                mPAdapterThree.setProductsItem(products);
                                                mPAdapterThree.notifyDataSetChanged();
                                            }
                                        }
                                    });
                        }
                    }

                });

        mCategoriesViewModel.setSubCategoriesLiveData(mCategories.get(3).getId());
        mCategoriesViewModel.getSubCategoriesLiveData()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        if (categories.size() > 0) {

                            if (mAdapterFour == null) {
                                mAdapterFour = new CategoriesFragmentAdapter(getContext(),
                                        categories, viewModelStoreOwner);
                                mBinding.recyclerViewCategoryFourCategoriesFragment
                                        .setAdapter(mAdapterFour);
                            } else {
                                mAdapterFour.setCategoriesItem(categories);
                                mAdapterFour.notifyDataSetChanged();
                            }

                        } else {
                            mCategoriesViewModel.
                                    setCategoryProductsLiveData(mCategories.get(3).getId());
                            mCategoriesViewModel.getCategoryProductsLiveData()
                                    .observe(lifecycleOwner, new Observer<List<Product>>() {
                                        @Override
                                        public void onChanged(List<Product> products) {
                                            if (mPAdapterFour == null) {
                                                mPAdapterFour = new CategoryProductsHorizontalAdapter(getContext(),
                                                        products);
                                                mBinding.recyclerViewCategoryFourCategoriesFragment
                                                        .setAdapter(mPAdapterFour);
                                            } else {
                                                mPAdapterFour.setProductsItem(products);
                                                mPAdapterFour.notifyDataSetChanged();
                                            }
                                        }
                                    });

                        }
                    }

                });

        mCategoriesViewModel.setSubCategoriesLiveData(mCategories.get(4).getId());
        mCategoriesViewModel.getSubCategoriesLiveData()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        if (categories.size() > 0) {

                            if (mAdapterFive == null) {
                                mAdapterFive = new CategoriesFragmentAdapter(getContext(),
                                        categories, viewModelStoreOwner);
                                mBinding.recyclerViewCategoryFiveCategoriesFragment
                                        .setAdapter(mAdapterFive);
                            } else {
                                mAdapterFive.setCategoriesItem(categories);
                                mAdapterFive.notifyDataSetChanged();
                            }

                        } else {
                            mCategoriesViewModel.
                                    setCategoryProductsLiveData(mCategories.get(4).getId());
                            mCategoriesViewModel.getCategoryProductsLiveData()
                                    .observe(lifecycleOwner, new Observer<List<Product>>() {
                                        @Override
                                        public void onChanged(List<Product> products) {
                                            if (mPAdapterFive == null) {
                                                mPAdapterFive = new CategoryProductsHorizontalAdapter(getContext(),
                                                        products);
                                                mBinding.
                                                        recyclerViewCategoryFiveCategoriesFragment
                                                        .setAdapter(mPAdapterFive);
                                            } else {
                                                mPAdapterFive.setProductsItem(products);
                                                mPAdapterFive.notifyDataSetChanged();
                                            }
                                        }
                                    });

                        }
                    }

                });

        mCategoriesViewModel.setSubCategoriesLiveData(mCategories.get(5).getId());
        mCategoriesViewModel.getSubCategoriesLiveData()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        if (categories.size() > 0) {

                            if (mAdapterSix == null) {
                                mAdapterSix = new CategoriesFragmentAdapter(getContext(),
                                        categories, viewModelStoreOwner);
                                mBinding.recyclerViewCategorySixCategoriesFragment
                                        .setAdapter(mAdapterSix);
                            } else {
                                mAdapterSix.setCategoriesItem(categories);
                                mAdapterSix.notifyDataSetChanged();
                            }

                        } else {
                            mCategoriesViewModel.
                                    setCategoryProductsLiveData(mCategories.get(5).getId());
                            mCategoriesViewModel.getCategoryProductsLiveData()
                                    .observe(lifecycleOwner, new Observer<List<Product>>() {
                                        @Override
                                        public void onChanged(List<Product> products) {
                                            if (mPAdapterSix == null) {
                                                mPAdapterSix = new CategoryProductsHorizontalAdapter(getContext(),
                                                        products);
                                                mBinding.recyclerViewCategorySixCategoriesFragment
                                                        .setAdapter(mPAdapterSix);
                                            } else {
                                                mPAdapterSix.setProductsItem(products);
                                                mPAdapterSix.notifyDataSetChanged();
                                            }
                                        }
                                    });

                        }
                    }
                });

    }

    private void setTextName(List<Category> items) {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (!names.equals(items.get(i).getName())) {
                names.add(items.get(i).getName());
            }
        }
        mBinding.textViewCategoryNameOneCategoriesFragment.setText(names.get(0));
        mBinding.textViewCategoryNameTwoCategoriesFragment.setText(names.get(1));
        mBinding.textViewCategoryNameThreeCategoriesFragment.setText(names.get(2));
        mBinding.textViewCategoryNameFourCategoriesFragment.setText(names.get(3));
        mBinding.textViewCategoryNameFiveCategoriesFragment.setText(names.get(4));
        mBinding.textViewCategoryNameSixCategoriesFragment.setText(names.get(5));

    }


}
