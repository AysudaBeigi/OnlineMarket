package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.CategoriesFragmentAdapter;
import com.example.onlinemarket.adapter.CategoryProductsHorizontalAdapter;
import com.example.onlinemarket.data.model.product.Category;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.repository.CategoryRepository;
import com.example.onlinemarket.data.repository.ProductRepository;
import com.example.onlinemarket.databinding.FragmentCategoriesBinding;
import com.example.onlinemarket.viewModel.CategoriesViewModel;
import com.example.onlinemarket.viewModel.HomeViewModel;

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


    /* private ProductRepository mProductRepository;
     private CategoryRepository mCategoryRepository;
     */
    private HomeViewModel mProductViewModel;
    private CategoriesViewModel mCategoriesViewModel;

    private List<Category> mCategories = new ArrayList<>();
    private FragmentCategoriesBinding mBinding;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductRepository = ProductRepository.getInstance(getContext());
        mCategoryRepository = new CategoryRepository(getContext());

        mCategoryRepository.setCategoriesLiveData(
                new CategoryRepository.CategoriesCallback() {
                    @Override
                    public void onItemResponse(List<Category> categories) {
                        mCategories = categories;
                        updateCategoriesRecyclerAdapter();
                        setTextName(categories);
                    }
                });

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


    private void updateCategoriesRecyclerAdapter() {
        mCategoryRepository.setSubCategoriesLiveData(mCategories.get(0).getId(),
                new CategoryRepository.subCategoriesCallback() {
                    @Override
                    public void onItemResponse(List<Category> subCategories) {
                        if (subCategories.size() > 0) {

                            if (mAdapterOne == null) {
                                mAdapterOne = new CategoriesFragmentAdapter(this, getContext(),
                                        subCategories);
                                mBinding.recyclerViewCategoryOneCategoriesFragment
                                        .setAdapter(mAdapterOne);
                            } else {
                                mAdapterOne.setCategoriesItem(subCategories);
                                mAdapterOne.notifyDataSetChanged();
                            }

                        } else {

                            mProductRepository.setCategoryProductsLiveData(mCategories.get(0).getId(),
                                    new ProductRepository.productsCallback() {
                                        @Override
                                        public void onItemResponse(List<Product> products) {
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
        mCategoryRepository.setSubCategoriesLiveData(mCategories.get(1).getId(),
                new CategoryRepository.subCategoriesCallback() {
                    @Override
                    public void onItemResponse(List<Category> subCategories) {
                        if (subCategories.size() > 0) {

                            if (mAdapterTwo == null) {
                                mAdapterTwo = new CategoriesFragmentAdapter(getContext(),
                                        subCategories);
                                mBinding.recyclerViewCategoryTwoCategoriesFragment
                                        .setAdapter(mAdapterTwo);
                            } else {
                                mAdapterTwo.setCategoriesItem(subCategories);
                                mAdapterTwo.notifyDataSetChanged();
                            }

                        } else {
                            mProductRepository.setCategoryProductsLiveData(mCategories.get(1).getId(),
                                    new ProductRepository.productsCallback() {
                                        @Override
                                        public void onItemResponse(List<Product> products) {
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
        mCategoryRepository.setSubCategoriesLiveData(mCategories.get(2).getId(),
                new CategoryRepository.subCategoriesCallback() {
                    @Override
                    public void onItemResponse(List<Category> subCategories) {
                        if (subCategories.size() > 0) {

                            if (mAdapterThree == null) {
                                mAdapterThree = new CategoriesFragmentAdapter(getContext(),
                                        subCategories);
                                mBinding.recyclerViewCategoryThreeCategoriesFragment
                                        .setAdapter(mAdapterThree);
                            } else {
                                mAdapterThree.setCategoriesItem(subCategories);
                                mAdapterThree.notifyDataSetChanged();
                            }

                        } else {
                            mProductRepository.setCategoryProductsLiveData(mCategories.get(2).getId(),
                                    new ProductRepository.productsCallback() {
                                        @Override
                                        public void onItemResponse(List<Product> products) {
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
        mCategoryRepository.setSubCategoriesLiveData(mCategories.get(3).getId(),
                new CategoryRepository.subCategoriesCallback() {
                    @Override
                    public void onItemResponse(List<Category> subCategories) {
                        if (subCategories.size() > 0) {

                            if (mAdapterFour == null) {
                                mAdapterFour = new CategoriesFragmentAdapter(getContext(),
                                        subCategories);
                                mBinding.recyclerViewCategoryFourCategoriesFragment
                                        .setAdapter(mAdapterFour);
                            } else {
                                mAdapterFour.setCategoriesItem(subCategories);
                                mAdapterFour.notifyDataSetChanged();
                            }

                        } else {
                            mProductRepository.setCategoryProductsLiveData(mCategories.get(3).getId(),
                                    new ProductRepository.productsCallback() {
                                        @Override
                                        public void onItemResponse(List<Product> products) {
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
        mCategoryRepository.setSubCategoriesLiveData(mCategories.get(4).getId(),
                new CategoryRepository.subCategoriesCallback() {
                    @Override
                    public void onItemResponse(List<Category> subCategories) {
                        if (subCategories.size() > 0) {

                            if (mAdapterFive == null) {
                                mAdapterFive = new CategoriesFragmentAdapter(getContext(),
                                        subCategories);
                                mBinding.recyclerViewCategoryFiveCategoriesFragment
                                        .setAdapter(mAdapterFive);
                            } else {
                                mAdapterFive.setCategoriesItem(subCategories);
                                mAdapterFive.notifyDataSetChanged();
                            }

                        } else {
                            mProductRepository.setCategoryProductsLiveData(mCategories.get(4).getId(),
                                    new ProductRepository.productsCallback() {
                                        @Override
                                        public void onItemResponse(List<Product> products) {
                                            if (mPAdapterFive == null) {
                                                mPAdapterFive = new CategoryProductsHorizontalAdapter(getContext(),
                                                        products);
                                                mBinding.recyclerViewCategoryFiveCategoriesFragment
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
        mCategoryRepository.setSubCategoriesLiveData(mCategories.get(5).getId(),
                new CategoryRepository.subCategoriesCallback() {
                    @Override
                    public void onItemResponse(List<Category> subCategories) {
                        if (subCategories.size() > 0) {

                            if (mAdapterSix == null) {
                                mAdapterSix = new CategoriesFragmentAdapter(getContext(),
                                        subCategories);
                                mBinding.recyclerViewCategorySixCategoriesFragment
                                        .setAdapter(mAdapterSix);
                            } else {
                                mAdapterSix.setCategoriesItem(subCategories);
                                mAdapterSix.notifyDataSetChanged();
                            }

                        } else {
                            mProductRepository.setCategoryProductsLiveData(mCategories.get(5).getId(),
                                    new ProductRepository.productsCallback() {
                                        @Override
                                        public void onItemResponse(List<Product> products) {
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
