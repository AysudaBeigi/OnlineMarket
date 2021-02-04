package com.example.onlinemarket.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ImageSliderAdapter;
import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Image;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.MarketRepository;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;


public class HomeFragment extends Fragment {

    private SliderView mSliderView;
    private ImageSliderAdapter mImageSliderAdapter;
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
        return view;
    }
    private void findViews(View view) {
        mSliderView = view.findViewById(R.id.fragment_home_slider);
        mRecyclerViewLastProducts = view.findViewById(R.id.fragment_home_recyclerview_recent_item);
        mRecyclerViewMostVisitedProducts = view.findViewById(R.id.fragment_home_recyclerview_most_visited);
        mRecyclerViewPopularProducts = view.findViewById(R.id.fragment_home_recyclerview_top_rated);
        mRecyclerCategories = view.findViewById(R.id.fragment_home_recyclerview_category);
        mRecyclerViewAmazingOffers = view.findViewById(R.id.fragment_home_recyclerview_offered_item);
        mEditTextSearch = view.findViewById(R.id.searching_query);

    }
    private void initViews() {
        mMarketRepository.fetchSingleProduct(608, new MarketRepository.SingleCallbacks() {
            @Override
            public void onItemResponse(Product item) {

                setupSliderAdapter(item.getImages());
            }
        });

        mMarketRepository.fetchRecentProducts(1, new MarketRepository.Callbacks() {
            @Override
            public void onItemResponse(List<Product> items) {
                initRecyclerAdapter(mRecyclerViewLastProducts,
                        mRecentProductAdapter, items);
            }
        });

        mMarketRepository.fetchMostVisitedProducts(1,
                new MarketRepository.Callbacks() {
            @Override
            public void onItemResponse(List<Product> items) {
                initRecyclerAdapter(mRecyclerViewMostVisitedProducts,
                        mMostVisitedProductAdapter, items);
            }
        });

        mMarketRepository.fetchRatedProducts(1,
                new MarketRepository.Callbacks() {
            @Override
            public void onItemResponse(List<Product> items) {
                initRecyclerAdapter(mRecyclerViewPopularProducts,
                        mRatedProductAdapter, items);
            }
        });
        mMarketRepository.fetchRatedProducts(2,
                new MarketRepository.Callbacks() {
            @Override
            public void onItemResponse(List<Product> items) {
                initRecyclerAdapter(mRecyclerViewAmazingOffers,
                        mAmazingAdapter, items);
            }
        });
        mMarketRepository.fetchCategory(1,
                new MarketRepository.CategoryCallbacks() {
            @Override
            public void onItemResponse(List<Category> items) {
                initCategoryRecyclerAdapter(mRecyclerCategories,
                        mCategoryAdapter, items);
            }
        });
    }
    private void initCategoryRecyclerAdapter(RecyclerView recyclerView,
                                             CategoryAdapter categoryAdapter,
                                             List<Category> categoriesItems) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        updateRecyclerCategoryAdapter(recyclerView, categoryAdapter, categoriesItems);
    }

    private void updateRecyclerCategoryAdapter(RecyclerView recyclerView,
                                               CategoryAdapter categoryAdapter,
                                               List<Category> categoriesItems) {

        if (categoryAdapter == null) {
            categoryAdapter = new CategoryAdapter(getContext(), categoriesItems);
            recyclerView.setAdapter(categoryAdapter);
        } else {
            categoryAdapter.setCategoriesItem(categoriesItems);
            categoryAdapter.notifyDataSetChanged();
        }
    }
    private void initRecyclerAdapter(RecyclerView recyclerView,
                                     ProductAdapter productAdapter,
                                     List<Product> productsItems) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        updateRecyclerAdapter(recyclerView, productAdapter, productsItems);
    }

    private void updateRecyclerAdapter(RecyclerView recyclerView,
                                       ProductAdapter productAdapter,
                                       List<ProductsItem> productsItems) {

        if (productAdapter == null) {
            productAdapter = new ProductAdapter(getContext(), productsItems);
            recyclerView.setAdapter(productAdapter);
        } else {
            productAdapter.setProductsItem(productsItems);
            productAdapter.notifyDataSetChanged();
        }
    }


    private void setupSliderAdapter(List<Image> imagesItems) {
        mSliderAdapter = new SliderAdapter(getContext(), imagesItems);
        mSliderView.setSliderAdapter(mSliderAdapter);
        mSliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        mSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
    }




}