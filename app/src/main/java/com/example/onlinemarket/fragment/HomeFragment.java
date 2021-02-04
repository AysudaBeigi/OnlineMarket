package com.example.onlinemarket.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.onlinemarket.IOnBackPress;
import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.CategoryAdapter;
import com.example.onlinemarket.adapter.ImageSliderAdapter;
import com.example.onlinemarket.adapter.ProductsAdapter;
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
    private ProductsAdapter mLastProductsAdapter;
    private ProductsAdapter mMostVisitedProductsAdapter;
    private ProductsAdapter mPopularProductsAdapter;
    private ProductsAdapter mAmazingOfferAdapter;
    private CategoryAdapter mCategoryAdapter;

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
        mMarketRepository.fetchProduct(608,
                new MarketRepository.SingleCallbacks() {
            @Override
            public void onItemResponse(Product item) {
                setupImageSliderAdapter(item.getImages());
            }
        });

        mMarketRepository.fetchLastProducts(1,
                new MarketRepository.Callbacks() {
            @Override
            public void onItemResponse(List<Product> items) {
                initRecyclerView(mRecyclerViewLastProducts);

                initProductAdapter(mRecyclerViewLastProducts,
                        mLastProductsAdapter,items);
            }
        });

        mMarketRepository.fetchMostVisitedProducts(1,
                new MarketRepository.Callbacks() {
            @Override
            public void onItemResponse(List<Product> items) {
                initRecyclerView(mRecyclerViewMostVisitedProducts);
                initProductAdapter(mRecyclerViewMostVisitedProducts,
                        mMostVisitedProductsAdapter, items);
            }
        });

        mMarketRepository.fetchPopularProducts(1,
                new MarketRepository.Callbacks() {
            @Override
            public void onItemResponse(List<Product> items) {
                initRecyclerView(mRecyclerViewPopularProducts);
                initProductAdapter(mRecyclerViewPopularProducts,
                        mPopularProductsAdapter, items);
            }
        });
        mMarketRepository.fetchPopularProducts(2,
                new MarketRepository.Callbacks() {
            @Override
            public void onItemResponse(List<Product> items) {
                initRecyclerView(mRecyclerViewAmazingOffers);
                initProductAdapter(mRecyclerViewAmazingOffers,
                        mAmazingOfferAdapter, items);
            }
        });
        mMarketRepository.fetchCategory(1,
                new MarketRepository.CategoryCallbacks() {
            @Override
            public void onItemResponse(List<Category> items) {
               initRecyclerView(mRecyclerCategories);
                initCategoryAdapter(items);
            }
        });
    }
    private void initCategoryAdapter(List<Category> categoriesItems) {


        if (mCategoryAdapter == null) {
            mCategoryAdapter = new CategoryAdapter(getContext(), categoriesItems);
            mRecyclerCategories.setAdapter(mCategoryAdapter);
        } else {
            mCategoryAdapter.setCategoriesItem(categoriesItems);
            mCategoryAdapter.notifyDataSetChanged();
        }
    }


    private void initProductAdapter(RecyclerView recyclerView,
                                    ProductsAdapter productsAdapter,
                                    List<Product> productsItems) {

        if (productsAdapter == null) {
            productsAdapter = new ProductsAdapter(getContext(), productsItems);
            recyclerView.setAdapter(productsAdapter);
        } else {
            productsAdapter.setProductsItem(productsItems);
            productsAdapter.notifyDataSetChanged();
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


    @Override
    public boolean onBackPressed() {
        return true;
    }


}