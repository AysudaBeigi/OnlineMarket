package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.HomeFragmentCategoriesAdapter;
import com.example.onlinemarket.adapter.HomeProductsHorizontalAdapter;
import com.example.onlinemarket.adapter.ImageSliderAdapter;
import com.example.onlinemarket.data.model.product.Category;
import com.example.onlinemarket.data.model.product.Image;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.repository.MarketRepository;
import com.example.onlinemarket.databinding.FragmentHomeBinding;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

import java.util.List;


public class HomeFragment extends Fragment   {

    private ImageSliderAdapter mImageSliderAdapter;
    private HomeProductsHorizontalAdapter mLastCategoryProductsHorizontalAdapter;
    private HomeProductsHorizontalAdapter mMostVisitedCategoryProductsHorizontalAdapter;
    private HomeProductsHorizontalAdapter mPopularCategoryProductsHorizontalAdapter;
    private HomeProductsHorizontalAdapter mWonderfulOfferAdapter;
    private HomeFragmentCategoriesAdapter mHomeFragmentCategoriesAdapter;

    private MarketRepository mMarketRepository;
    private NavController mNavController;
    private FragmentHomeBinding mBinding;

    public static String TAG = "OnlineMarket";

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
        mBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_home,
                container, false);

        initViews();
        setListeners();
        return mBinding.getRoot();
    }

    private void setListeners() {
        mBinding.searchViewHome.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

   /* private void findViews(View view) {
        mSliderView = view.
                findViewById(R.id.fragment_home_slider);
        mRecyclerViewLastProducts = view.
                findViewById(R.id.recycler_view_latest);
        mRecyclerViewMostVisitedProducts = view.
                findViewById(R.id.recycler_view_most_viewed);
        mRecyclerViewPopularProducts = view.
                findViewById(R.id.recycler_view_popularest);
        mRecyclerCategories = view.
                findViewById(R.id.recycler_view_categories_home_fragment);
        mRecyclerViewWonderfulOffer = view.
                findViewById(R.id.recycler_view_wonderful_offer);
        mSearchViewHomeFragment = view.
                findViewById(R.id.search_view);

    }*/

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
                        Log.d(TAG, "fetchLastProducts++onItemResponse"
                                + items.get(0).getName());
                        initRecyclerView(mBinding.recyclerViewLatest,
                                mLastCategoryProductsHorizontalAdapter, items);

                    }
                });

        mMarketRepository.fetchMostVisitedProducts(
                new MarketRepository.productsCallback() {
                    @Override
                    public void onItemResponse(List<Product> items) {
                        Log.d(TAG, "fetchMostVisitedProducts++onItemResponse" +
                                items.get(0).getName());

                        initRecyclerView(mBinding.recyclerViewMostViewed
                                , mMostVisitedCategoryProductsHorizontalAdapter, items);

                    }
                });

        mMarketRepository.fetchPopularProducts(1,
                new MarketRepository.productsCallback() {
                    @Override
                    public void onItemResponse(List<Product> items) {
                        initRecyclerView(mBinding.recyclerViewPopularest,
                                mPopularCategoryProductsHorizontalAdapter, items);

                    }
                });
        mMarketRepository.fetchPopularProducts(2,
                new MarketRepository.productsCallback() {
                    @Override
                    public void onItemResponse(List<Product> items) {
                        Log.d(TAG, "fetchAmazing++onItemResponse" +
                                items.get(0).getName());

                        initRecyclerView(mBinding.recyclerViewWonderfulOffer
                                , mWonderfulOfferAdapter, items);

                    }
                });
        mMarketRepository.fetchCategories(
                new MarketRepository.CategoriesCallback() {
                    @Override
                    public void onItemResponse(List<Category> categories) {
                        mBinding.recyclerViewCategoriesHomeFragment.setLayoutManager(new LinearLayoutManager(getContext(),
                                LinearLayoutManager.HORIZONTAL, false));
                        initCategoryAdapter(categories);
                    }
                });
    }


    private void initRecyclerView(RecyclerView recyclerView,
                                  HomeProductsHorizontalAdapter adapter,
                                  List<Product> products) {
        Log.d(TAG, "HomeF : initRecyclerView");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));


        if (adapter == null) {
            adapter = new HomeProductsHorizontalAdapter(getContext(),
                    products);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setProductsItem(products);
            adapter.notifyDataSetChanged();

        }
        Log.d(TAG, "adapter is :" + adapter.toString());
        Log.d(TAG, "recyclerview is  is :" + recyclerView.toString());
        Log.d(TAG, "last product name  is :" + products.get(products.size() - 1).getName());
    }


    private void initCategoryAdapter(List<Category> categoriesItems) {
        Log.d(TAG, "HomeF : initCategoryAdapter");


        if (mHomeFragmentCategoriesAdapter == null) {
            mHomeFragmentCategoriesAdapter =
                    new HomeFragmentCategoriesAdapter(getContext(),
                            categoriesItems);
            mBinding.recyclerViewCategoriesHomeFragment.setAdapter(mHomeFragmentCategoriesAdapter);
        } else {
            mHomeFragmentCategoriesAdapter.setCategoriesItem(categoriesItems);
            mHomeFragmentCategoriesAdapter.notifyDataSetChanged();
        }
    }


    private void setupImageSliderAdapter(List<Image> imagesItems) {
        mImageSliderAdapter = new
                ImageSliderAdapter(getContext(), imagesItems);
        mBinding.sliderViewHome.setSliderAdapter(mImageSliderAdapter);
        mBinding.sliderViewHome.setIndicatorAnimation(IndicatorAnimationType.WORM);
        mBinding.sliderViewHome.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController=Navigation.findNavController(view);
    }

    private void replaceSearchResultFragment(String query) {

        Bundle bundle=new Bundle();
        bundle.putString(SearchResultFragment.ARGS_QUERY,query);
        bundle.putInt(SearchResultFragment.ARGS_CATEGORY_ID,-1);
        mNavController.navigate(R.id.action_HomeFragment_to_SearchResultFragment
        ,bundle);


    }



}