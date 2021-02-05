package com.example.onlinemarket.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.onlinemarket.IOnBackPress;
import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.CategoriesAdapter;
import com.example.onlinemarket.adapter.ImageSliderAdapter;
import com.example.onlinemarket.adapter.ProductsHorizontalAdapter;
import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Image;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.MarketRepository;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;


public class HomeFragment extends Fragment  implements IOnBackPress {

    private SliderView mSliderView;
    private ImageSliderAdapter mImageSliderAdapter;
    private ProductsHorizontalAdapter mLastProductsHorizontalAdapter;
    private ProductsHorizontalAdapter mMostVisitedProductsHorizontalAdapter;
    private ProductsHorizontalAdapter mPopularProductsHorizontalAdapter;
    private ProductsHorizontalAdapter mAmazingOfferAdapter;
    private CategoriesAdapter mCategoriesAdapter;

    private EditText mEditTextSearch;
    private RecyclerView mRecyclerViewLastProducts;
    private RecyclerView mRecyclerViewMostVisitedProducts;
    private RecyclerView mRecyclerViewPopularProducts;
    private RecyclerView mRecyclerCategories;
    private RecyclerView mRecyclerViewAmazingOffers;
    private MarketRepository mMarketRepository;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
         fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMarketRepository= new MarketRepository(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home,
                container, false);

        findViews(view);
        initViews();
        setListeners();
        return view;
    }
    private void findViews(View view) {
        mSliderView = view.findViewById(R.id.fragment_home_slider);
        mRecyclerViewLastProducts = view.findViewById(R.id.fragment_home_recycler_view_most_viewed);
        mRecyclerViewMostVisitedProducts = view.findViewById(R.id.fragment_home_recycler_view_last_products);
        mRecyclerViewPopularProducts = view.findViewById(R.id.fragment_home_recycler_view_popularest_products);
        mRecyclerCategories = view.findViewById(R.id.fragment_home_recyclerview_categories);
        mRecyclerViewAmazingOffers = view.findViewById(R.id.fragment_home_recycler_view_wonderful_offer);
        mEditTextSearch = view.findViewById(R.id.searching_query);

    }
    private void initViews() {
        mMarketRepository.fetchProduct(608,
                new MarketRepository.productCallback() {
            @Override
            public void onItemResponse(Product item) {

                setupImageSliderAdapter(item.getImages());
            }
        });

        mMarketRepository.fetchLastProducts(1,
                new MarketRepository.productsCallback() {
            @Override
            public void onItemResponse(List<Product> items) {
                initRecyclerView(mRecyclerViewLastProducts);

                initProductAdapter(mRecyclerViewLastProducts,
                        mLastProductsHorizontalAdapter,items);
            }
        });

        mMarketRepository.fetchMostVisitedProducts(1,
                new MarketRepository.productsCallback() {
            @Override
            public void onItemResponse(List<Product> items) {
                initRecyclerView(mRecyclerViewMostVisitedProducts);
                initProductAdapter(mRecyclerViewMostVisitedProducts,
                        mMostVisitedProductsHorizontalAdapter, items);
            }
        });

        mMarketRepository.fetchPopularProducts(1,
                new MarketRepository.productsCallback() {
            @Override
            public void onItemResponse(List<Product> items) {
                initRecyclerView(mRecyclerViewPopularProducts);
                initProductAdapter(mRecyclerViewPopularProducts,
                        mPopularProductsHorizontalAdapter, items);
            }
        });
        mMarketRepository.fetchPopularProducts(2,
                new MarketRepository.productsCallback() {
            @Override
            public void onItemResponse(List<Product> items) {
                initRecyclerView(mRecyclerViewAmazingOffers);
                initProductAdapter(mRecyclerViewAmazingOffers,
                        mAmazingOfferAdapter, items);
            }
        });
        mMarketRepository.fetchCategories(1,
                new MarketRepository.CategoriesCallback() {
            @Override
            public void onItemResponse(List<Category> items) {
               initRecyclerView(mRecyclerCategories);
                initCategoryAdapter(items);
            }
        });
    }
    private void initCategoryAdapter(List<Category> categoriesItems) {


        if (mCategoriesAdapter == null) {
            mCategoriesAdapter = new CategoriesAdapter(getContext(), categoriesItems);
            mRecyclerCategories.setAdapter(mCategoriesAdapter);
        } else {
            mCategoriesAdapter.setCategoriesItem(categoriesItems);
            mCategoriesAdapter.notifyDataSetChanged();
        }
    }


    private void initProductAdapter(RecyclerView recyclerView,
                                    ProductsHorizontalAdapter productsHorizontalAdapter,
                                    List<Product> productsItems) {

        if (productsHorizontalAdapter == null) {
            productsHorizontalAdapter = new ProductsHorizontalAdapter(getContext(), productsItems);
            recyclerView.setAdapter(productsHorizontalAdapter);
        } else {
            productsHorizontalAdapter.setProductsItem(productsItems);
            productsHorizontalAdapter.notifyDataSetChanged();
        }
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
    }


    private void setupImageSliderAdapter(List<Image> imagesItems) {
        ImageSliderAdapter imageSliderAdapter = new
                ImageSliderAdapter(getContext(), imagesItems);
        mSliderView.setSliderAdapter(imageSliderAdapter);
        mSliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        mSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
    }

    private void setListeners() {
        mEditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }


            public void afterTextChanged(Editable s) {
                getQueryEditText(s.toString());
            }
        });


    }


    private void getQueryEditText(String query) {

        if (query.length() > 2)
            ((AppCompatActivity) getContext()).
                    getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_main_activity,
                            SearchFragment.newInstance(query))
                    .commit();
    }


    @Override
    public boolean onBackPressed() {
        return true;
    }


}