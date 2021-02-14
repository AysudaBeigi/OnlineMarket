package com.example.onlinemarket.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.IOnBackPress;
import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.CategoriesFragmentAdapter;
import com.example.onlinemarket.adapter.ProductHorizontalAdapter;
import com.example.onlinemarket.model.product.Category;
import com.example.onlinemarket.model.product.Product;
import com.example.onlinemarket.repository.MarketRepository;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment implements IOnBackPress {
    public static final String TAG = "CategoryFragment";

    private RecyclerView mRecyclerViewCategoryOne, mRecyclerViewCategoryTwo,
            mRecyclerViewCategoryThree, mRecyclerViewCategoryFour,
            mRecyclerViewCategoryFive,
            mRecyclerViewCategorySix;

    private CategoriesFragmentAdapter mAdapterOne, mAdapterTwo, mAdapterThree,
            mAdapterFour, mAdapterFive,
            mAdapterSix;
    private ProductHorizontalAdapter mPAdapterOne, mPAdapterTwo, mPAdapterThree,
            mPAdapterFour, mPAdapterFive,
            mPAdapterSix;

    private MaterialTextView mTextOne, mTextTwo, mTextThree, mTextFour, mTextFive, mTextSix;

    private MarketRepository mMarketRepository;
    private List<Category> mCategories = new ArrayList<>();

    public CategoriesFragment() {
        // Required empty public constructor
    }

    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMarketRepository = new MarketRepository(getContext());

        mMarketRepository.fetchCategories(
                new MarketRepository.CategoriesCallback() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories,
                container, false);

        findViews(view);

        return view;
    }

    private void findViews(View view) {
        mRecyclerViewCategoryOne = view.findViewById(R.id.recycler_view_category_one_categories_fragment);
        mRecyclerViewCategoryTwo = view.findViewById(R.id.recycler_view_category_two_categories_fragment);
        mRecyclerViewCategoryThree = view.findViewById(R.id.recycler_view_category_three_categories_fragment);
        mRecyclerViewCategoryFour = view.findViewById(R.id.recycler_view_category_four_categories_fragment);
        mRecyclerViewCategoryFive = view.findViewById(R.id.recycler_view_category_five_categories_fragment);
        mRecyclerViewCategorySix = view.findViewById(R.id.recycler_view_category_six_categories_fragment);

        mTextOne = view.findViewById(R.id.text_view_category_name_one_categories_fragment);
        mTextTwo = view.findViewById(R.id.text_view_category_name_two_categories_fragment);
        mTextThree = view.findViewById(R.id.text_view_category_name_three_categories_fragment);
        mTextFour = view.findViewById(R.id.text_view_category_name_four_categories_fragment);
        mTextFive = view.findViewById(R.id.text_view_category_name_five_categories_fragment);
        mTextSix = view.findViewById(R.id.text_view_category_name_six_categories_fragment);

    }

    private void updateCategoriesRecyclerAdapter() {
        mMarketRepository.fetchSubCategories(mCategories.get(0).getId(),
                new MarketRepository.subCategoriesCallback() {
                    @Override
                    public void onItemResponse(List<Category> subCategories) {
                        if (subCategories.size() > 0) {

                            if (mAdapterOne == null) {
                                mAdapterOne = new CategoriesFragmentAdapter(getContext(),
                                        subCategories);
                                mRecyclerViewCategoryOne.setAdapter(mAdapterOne);
                            } else {
                                mAdapterOne.setCategoriesItem(subCategories);
                                mAdapterOne.notifyDataSetChanged();
                            }

                        } else {
                            mMarketRepository.fetchCategoryProduct(mCategories.get(0).getId(),
                                    new MarketRepository.productsCallback() {
                                        @Override
                                        public void onItemResponse(List<Product> products) {
                                            if (mPAdapterOne == null) {
                                                mPAdapterOne = new ProductHorizontalAdapter(getContext(),
                                                        products);
                                                mRecyclerViewCategoryOne.setAdapter(mPAdapterOne);
                                            } else {
                                                mPAdapterOne.setProductsItem(products);
                                                mPAdapterOne.notifyDataSetChanged();
                                            }
                                        }
                                    });
                        }
                    }

                });
        mMarketRepository.fetchSubCategories(mCategories.get(1).getId(),
                new MarketRepository.subCategoriesCallback() {
                    @Override
                    public void onItemResponse(List<Category> subCategories) {
                        if (subCategories.size() > 0) {

                            if (mAdapterTwo == null) {
                                mAdapterTwo = new CategoriesFragmentAdapter(getContext(),
                                        subCategories);
                                mRecyclerViewCategoryTwo.setAdapter(mAdapterTwo);
                            } else {
                                mAdapterTwo.setCategoriesItem(subCategories);
                                mAdapterTwo.notifyDataSetChanged();
                            }

                        } else {
                            mMarketRepository.fetchCategoryProduct(mCategories.get(1).getId(),
                                    new MarketRepository.productsCallback() {
                                        @Override
                                        public void onItemResponse(List<Product> products) {
                                            if (mPAdapterTwo == null) {
                                                mPAdapterTwo = new ProductHorizontalAdapter(
                                                        getContext(),
                                                        products);
                                                mRecyclerViewCategoryTwo.setAdapter(mPAdapterTwo);
                                            } else {
                                                mPAdapterTwo.setProductsItem(products);
                                                mPAdapterTwo.notifyDataSetChanged();
                                            }
                                        }
                                    });
                        }
                    }

                });
        mMarketRepository.fetchSubCategories(mCategories.get(2).getId(),
                new MarketRepository.subCategoriesCallback() {
                    @Override
                    public void onItemResponse(List<Category> subCategories) {
                        if (subCategories.size() > 0) {

                            if (mAdapterThree == null) {
                                mAdapterThree = new CategoriesFragmentAdapter(getContext(),
                                        subCategories);
                                mRecyclerViewCategoryThree.setAdapter(mAdapterThree);
                            } else {
                                mAdapterThree.setCategoriesItem(subCategories);
                                mAdapterThree.notifyDataSetChanged();
                            }

                        } else {
                            mMarketRepository.fetchCategoryProduct(mCategories.get(2).getId(),
                                    new MarketRepository.productsCallback() {
                                        @Override
                                        public void onItemResponse(List<Product> products) {
                                            if (mPAdapterThree == null) {
                                                mPAdapterThree = new ProductHorizontalAdapter(getContext(),
                                                        products);
                                                mRecyclerViewCategoryThree.setAdapter(mPAdapterThree);
                                            } else {
                                                mPAdapterThree.setProductsItem(products);
                                                mPAdapterThree.notifyDataSetChanged();
                                            }
                                        }
                                    });
                        }
                    }

                });
        mMarketRepository.fetchSubCategories(mCategories.get(3).getId(),
                new MarketRepository.subCategoriesCallback() {
                    @Override
                    public void onItemResponse(List<Category> subCategories) {
                        if (subCategories.size() > 0) {

                            if (mAdapterFour == null) {
                                mAdapterFour = new CategoriesFragmentAdapter(getContext(),
                                        subCategories);
                                mRecyclerViewCategoryFour.setAdapter(mAdapterFour);
                            } else {
                                mAdapterFour.setCategoriesItem(subCategories);
                                mAdapterFour.notifyDataSetChanged();
                            }

                        } else {
                            mMarketRepository.fetchCategoryProduct(mCategories.get(3).getId(),
                                    new MarketRepository.productsCallback() {
                                        @Override
                                        public void onItemResponse(List<Product> products) {
                                            if (mPAdapterFour == null) {
                                                mPAdapterFour = new ProductHorizontalAdapter(getContext(),
                                                        products);
                                                mRecyclerViewCategoryFour.setAdapter(mPAdapterFour);
                                            } else {
                                                mPAdapterFour.setProductsItem(products);
                                                mPAdapterFour.notifyDataSetChanged();
                                            }
                                        }
                                    });
                        }
                    }

                });
        mMarketRepository.fetchSubCategories(mCategories.get(4).getId(),
                new MarketRepository.subCategoriesCallback() {
                    @Override
                    public void onItemResponse(List<Category> subCategories) {
                        if (subCategories.size() > 0) {

                            if (mAdapterFive == null) {
                                mAdapterFive = new CategoriesFragmentAdapter(getContext(),
                                        subCategories);
                                mRecyclerViewCategoryFive.setAdapter(mAdapterFive);
                            } else {
                                mAdapterFive.setCategoriesItem(subCategories);
                                mAdapterFive.notifyDataSetChanged();
                            }

                        } else {
                            mMarketRepository.fetchCategoryProduct(mCategories.get(4).getId(),
                                    new MarketRepository.productsCallback() {
                                        @Override
                                        public void onItemResponse(List<Product> products) {
                                            if (mPAdapterFive == null) {
                                                mPAdapterFive = new ProductHorizontalAdapter(getContext(),
                                                        products);
                                                mRecyclerViewCategoryFive.setAdapter(mPAdapterFive);
                                            } else {
                                                mPAdapterFive.setProductsItem(products);
                                                mPAdapterFive.notifyDataSetChanged();
                                            }
                                        }
                                    });
                        }
                    }

                });
        mMarketRepository.fetchSubCategories(mCategories.get(5).getId(),
                new MarketRepository.subCategoriesCallback() {
                    @Override
                    public void onItemResponse(List<Category> subCategories) {
                        if (subCategories.size() > 0) {

                            if (mAdapterSix == null) {
                                mAdapterSix = new CategoriesFragmentAdapter(getContext(),
                                        subCategories);
                                mRecyclerViewCategorySix.setAdapter(mAdapterSix);
                            } else {
                                mAdapterSix.setCategoriesItem(subCategories);
                                mAdapterSix.notifyDataSetChanged();
                            }

                        } else {
                            mMarketRepository.fetchCategoryProduct(mCategories.get(5).getId(),
                                    new MarketRepository.productsCallback() {
                                        @Override
                                        public void onItemResponse(List<Product> products) {
                                            if (mPAdapterSix == null) {
                                                mPAdapterSix = new ProductHorizontalAdapter(getContext(),
                                                        products);
                                                mRecyclerViewCategorySix.setAdapter(mPAdapterSix);
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
        mTextOne.setText(names.get(0));
        mTextTwo.setText(names.get(1));
        mTextThree.setText(names.get(2));
        mTextFour.setText(names.get(3));
        mTextFive.setText(names.get(4));
        mTextSix.setText(names.get(5));

    }


    @Override
    public boolean onBackPressed() {
        return true;
    }
}
