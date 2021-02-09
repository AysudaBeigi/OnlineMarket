package com.example.onlinemarket.controller.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.IOnBackPress;
import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.HomeFragmentCategoriesAdapter;
import com.example.onlinemarket.adapter.ImageSliderAdapter;
import com.example.onlinemarket.adapter.ProductsHorizontalAdapter;
import com.example.onlinemarket.model.product.Category;
import com.example.onlinemarket.model.product.Image;
import com.example.onlinemarket.model.product.Product;
import com.example.onlinemarket.repository.MarketRepository;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;


public class HomeFragment extends Fragment implements IOnBackPress {

    private SliderView mSliderView;
    private ImageSliderAdapter mImageSliderAdapter;
    private ProductsHorizontalAdapter mLastProductsHorizontalAdapter;
    private ProductsHorizontalAdapter mMostVisitedProductsHorizontalAdapter;
    private ProductsHorizontalAdapter mPopularProductsHorizontalAdapter;
    private ProductsHorizontalAdapter mAmazingOfferAdapter;
    private HomeFragmentCategoriesAdapter mHomeFragmentCategoriesAdapter;

    private SearchView mSearchViewHomeFragment;
    private RecyclerView mRecyclerViewLastProducts;
    private RecyclerView mRecyclerViewMostVisitedProducts;
    private RecyclerView mRecyclerViewPopularProducts;
    private RecyclerView mRecyclerCategories;
    private RecyclerView mRecyclerViewWonderfulOffer;
    private MarketRepository mMarketRepository;

    public static String TAG="OnlineMarket";
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
        mMarketRepository = new MarketRepository(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,
                container, false);

        findViews(view);
        initViews();
        setListeners();
        return view;
    }

    private void setListeners() {
        mSearchViewHomeFragment.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                replaceSearchResultFragment(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void findViews(View view) {
        mSliderView = view.
                findViewById(R.id.fragment_home_slider);
        mRecyclerViewLastProducts = view.
                findViewById(R.id.fragment_home_recycler_view_last_products);
        mRecyclerViewMostVisitedProducts = view.
                findViewById(R.id.fragment_home_recycler_view_most_viewed);
        mRecyclerViewPopularProducts = view.
                findViewById(R.id.fragment_home_recycler_view_popularest_products);
        mRecyclerCategories = view.
                findViewById(R.id.fragment_home_recyclerview_categories);
        mRecyclerViewWonderfulOffer = view.
                findViewById(R.id.fragment_home_recycler_view_wonderful_offer);
        mSearchViewHomeFragment = view.
                findViewById(R.id.search_view);

    }

    private void initViews() {
        mMarketRepository.fetchProduct(608,
                new MarketRepository.productCallback() {
                    @Override
                    public void onItemResponse(Product product) {

                        setupImageSliderAdapter(product.getImages());
                    }
                });

        mMarketRepository.fetchLastProducts(
                new MarketRepository.productsCallback() {
                    @Override
                    public void onItemResponse(List<Product> items) {
                    Log.d(TAG,"fetchLastProducts++onItemResponse"
                            +items.get(0).getName());
                        initRecyclerView(mRecyclerViewLastProducts,
                                mLastProductsHorizontalAdapter, items);

                    }
                });

        mMarketRepository.fetchMostVisitedProducts(
                new MarketRepository.productsCallback() {
                    @Override
                    public void onItemResponse(List<Product> items) {
                        Log.d(TAG,"fetchMostVisitedProducts++onItemResponse"+
                                items.get(0).getName());

                        initRecyclerView(mRecyclerViewMostVisitedProducts
                                , mMostVisitedProductsHorizontalAdapter, items);

                    }
                });

        mMarketRepository.fetchPopularProducts(1,
                new MarketRepository.productsCallback() {
                    @Override
                    public void onItemResponse(List<Product> items) {
                        initRecyclerView(mRecyclerViewPopularProducts,
                                mPopularProductsHorizontalAdapter,items );

                    }
                });
        mMarketRepository.fetchPopularProducts(2,
                new MarketRepository.productsCallback() {
                    @Override
                    public void onItemResponse(List<Product> items) {
                        Log.d(TAG,"fetchAmazing++onItemResponse"+
                                items.get(0).getName());

                        initRecyclerView(mRecyclerViewWonderfulOffer
                        ,mAmazingOfferAdapter,items);

                    }
                });
        mMarketRepository.fetchCategories(
                new MarketRepository.CategoriesCallback() {
                    @Override
                    public void onItemResponse(List<Category> categories) {
                        mRecyclerCategories.setLayoutManager(new LinearLayoutManager(getContext(),
                                LinearLayoutManager.HORIZONTAL, false));
                        initCategoryAdapter(categories);
                    }
                });
    }


    private void initRecyclerView(RecyclerView recyclerView,
                                  ProductsHorizontalAdapter adapter,
                                  List<Product> products) {
        Log.d(TAG,"HomeF : initRecyclerView");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        initProductAdapter(recyclerView, adapter, products);
    }

    private void initProductAdapter(RecyclerView recyclerView,
                                    ProductsHorizontalAdapter productsHorizontalAdapter,
                                    List<Product> productsItems) {
        Log.d(TAG,"HomeF : initProductAdapter");


        if (productsHorizontalAdapter == null) {
            productsHorizontalAdapter = new ProductsHorizontalAdapter(getContext(),
                    productsItems);
            recyclerView.setAdapter(productsHorizontalAdapter);
        } else {
            productsHorizontalAdapter.setProductsItem(productsItems);
            productsHorizontalAdapter.notifyDataSetChanged();

        }
    }

    private void initCategoryAdapter(List<Category> categoriesItems) {
        Log.d(TAG,"HomeF : initCategoryAdapter");


        if (mHomeFragmentCategoriesAdapter == null) {
            mHomeFragmentCategoriesAdapter =
                    new HomeFragmentCategoriesAdapter(getContext(),
                    categoriesItems);
            mRecyclerCategories.setAdapter(mHomeFragmentCategoriesAdapter);
        } else {
            mHomeFragmentCategoriesAdapter.setCategoriesItem(categoriesItems);
            mHomeFragmentCategoriesAdapter.notifyDataSetChanged();
        }
    }


    private void setupImageSliderAdapter(List<Image> imagesItems) {
       mImageSliderAdapter = new
                ImageSliderAdapter(getContext(), imagesItems);
        mSliderView.setSliderAdapter(mImageSliderAdapter);
        mSliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        mSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
    }

    private void replaceSearchResultFragment(String query) {

        ((AppCompatActivity) getContext()).
                getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_main_activity,
                        SearchResultFragment.
                                newInstance(query, -1))
                .commit();
    }


    @Override
    public boolean onBackPressed() {
        return true;
    }


}