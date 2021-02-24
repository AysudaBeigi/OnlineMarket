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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.HomeCategoriesAdapter;
import com.example.onlinemarket.adapter.HomeProductsAdapter;
import com.example.onlinemarket.adapter.ImageSliderAdapter;
import com.example.onlinemarket.data.model.product.Category;
import com.example.onlinemarket.data.model.product.Image;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.remote.NetworkParams;
import com.example.onlinemarket.databinding.FragmentHomeBinding;
import com.example.onlinemarket.utils.OnlineMarketPreferences;
import com.example.onlinemarket.viewModel.HomeViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

import java.util.List;


public class HomeFragment extends Fragment {

    private ImageSliderAdapter mImageSliderAdapter;
    private HomeProductsAdapter mLastCategoryProductsHorizontalAdapter;
    private HomeProductsAdapter mMostVisitedCategoryProductsHorizontalAdapter;
    private HomeProductsAdapter mPopularCategoryProductsHorizontalAdapter;
    private HomeProductsAdapter mAmazingOfferAdapter;
    private HomeCategoriesAdapter mHomeCategoriesAdapter;

    private HomeViewModel mHomeViewModel;
    private NavController mNavController;
    private FragmentHomeBinding mBinding;

    public static String TAG = "OnlineMarket";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "HomeF : onCreate");

        mHomeViewModel = new ViewModelProvider(this).
                get(HomeViewModel.class);

        initData();

    }

    private void initData() {
        mHomeViewModel.setCategoriesLiveData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "HomeF : onCreateView");

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,
                container, false);

        initViews();
        setObservers();
        setListeners();
        return mBinding.getRoot();
    }

    private void initViews() {
        initRecyclerViews(mBinding.recyclerViewWonderfulOffer);
        initRecyclerViews(mBinding.recyclerViewPopularest);
        initRecyclerViews(mBinding.recyclerViewLatest);
        initRecyclerViews(mBinding.recyclerViewMostViewed);
        initRecyclerViews(mBinding.recyclerViewCategoriesHomeFragment);
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

    private void setObservers() {
        Log.d(TAG, "HomeF : setObservers");

        mHomeViewModel.getSpecialProductLiveData().
                observe(this, new Observer<Product>() {
                    @Override
                    public void onChanged(Product product) {
                        Log.d(TAG, "HomeF : getSpecialProductLiveData: onChanged ");

                        setupImageSliderAdapter(product.getImages());

                    }
                });


        mHomeViewModel.getLatestProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        Log.d(TAG, "HomeF : getLatestProductsLiveData: onChanged ");

                        setupProductAdapter(mBinding.recyclerViewLatest,
                                mLastCategoryProductsHorizontalAdapter, products);

                    }
                });

        mHomeViewModel.getMostVisitedProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        Log.d(TAG, "HomeF : getMostVisitedProductsLiveData: onChanged ");

                        setupProductAdapter(mBinding.recyclerViewMostViewed
                                , mMostVisitedCategoryProductsHorizontalAdapter, products);

                    }
                });


        mHomeViewModel.getPopularProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        Log.d(TAG, "HomeF : getPopularProductsLiveData: onChanged ");

                        setupProductAdapter(mBinding.recyclerViewPopularest,
                                mPopularCategoryProductsHorizontalAdapter, products);

                    }
                });

        mHomeViewModel.getAmazingOfferProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        Log.d(TAG, "HomeF : getAmazingOfferProductsLiveData: onChanged ");

                        setupProductAdapter(mBinding.recyclerViewWonderfulOffer
                                , mAmazingOfferAdapter, products);

                    }
                });
        mHomeViewModel.getParentCategoriesLiveData()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        Log.d(TAG, "HomeF : getParentCategoriesLiveData: onChanged ");

                        setupCategoryAdapter(categories);

                    }
                });

    }


    private void setupProductAdapter(RecyclerView recyclerView,
                                     HomeProductsAdapter adapter,
                                     List<Product> products) {
        Log.d(TAG, "HomeF : setupRecyclerView");


        adapter = new HomeProductsAdapter(getContext(),
                products, this);
        recyclerView.setAdapter(adapter);

        Log.d(TAG, "adapter is :" + adapter.toString());
        Log.d(TAG, "recyclerview is  is :" + recyclerView.toString());
        Log.d(TAG, "last product name  is :" + products.get(products.size() - 1).
                getName());
    }

    private void initRecyclerViews(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
    }


    private void setupCategoryAdapter(List<Category> categories) {
        Log.d(TAG, "HomeF : initCategoryAdapter");

        mHomeCategoriesAdapter =
                new HomeCategoriesAdapter(getContext(),
                        categories, this);
        mBinding.recyclerViewCategoriesHomeFragment.
                setAdapter(mHomeCategoriesAdapter);
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
        mNavController = Navigation.findNavController(view);
    }

    private void replaceSearchResultFragment(String query) {
        OnlineMarketPreferences.getInstance(getActivity())
                .setQueryMap(NetworkParams.getSearchAllProducts(query));
        mHomeViewModel.
                setSearchResultProductsLiveData(NetworkParams.getSearchAllProducts(query));
        mNavController.navigate(R.id.action_HomeFragment_to_SearchResultFragment);

    }


}