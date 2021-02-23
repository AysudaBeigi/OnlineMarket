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
        mHomeViewModel = new ViewModelProvider(this).
                get(HomeViewModel.class);
        //setProductsLiveData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,
                container, false);

        setObservers();
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

    private void setObservers() {
        mHomeViewModel.getSpecialProductLiveData().
                observe(this, new Observer<Product>() {
                    @Override
                    public void onChanged(Product product) {
                        setupImageSliderAdapter(product.getImages());

                    }
                });


        mHomeViewModel.getLatestProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        setupRecyclerView(mBinding.recyclerViewLatest,
                                mLastCategoryProductsHorizontalAdapter, products);

                    }
                });

        mHomeViewModel.getMostVisitedProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {

                        setupRecyclerView(mBinding.recyclerViewMostViewed
                                , mMostVisitedCategoryProductsHorizontalAdapter, products);

                    }
                });


        mHomeViewModel.getPopularProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        setupRecyclerView(mBinding.recyclerViewPopularest,
                                mPopularCategoryProductsHorizontalAdapter, products);

                    }
                });

        mHomeViewModel.getAmazingOfferProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        setupRecyclerView(mBinding.recyclerViewWonderfulOffer
                                , mAmazingOfferAdapter, products);

                    }
                });
        mHomeViewModel.getCategoriesLiveData()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        mBinding.recyclerViewCategoriesHomeFragment.
                                setLayoutManager(new LinearLayoutManager(getContext(),
                                        LinearLayoutManager.HORIZONTAL, false));
                        setupCategoryAdapter(categories);

                    }
                });

    }


    private void setupRecyclerView(RecyclerView recyclerView,
                                   HomeProductsAdapter adapter,
                                   List<Product> products) {
        Log.d(TAG, "HomeF : initRecyclerView");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        if (adapter == null) {
            adapter = new HomeProductsAdapter(getContext(),
                    products,this);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setProduct(products);
            adapter.notifyDataSetChanged();

        }
        Log.d(TAG, "adapter is :" + adapter.toString());
        Log.d(TAG, "recyclerview is  is :" + recyclerView.toString());
        Log.d(TAG, "last product name  is :" + products.get(products.size() - 1).
                getName());
    }


    private void setupCategoryAdapter(List<Category> categories) {
        Log.d(TAG, "HomeF : initCategoryAdapter");


        if (mHomeCategoriesAdapter == null) {
            mHomeCategoriesAdapter =
                    new HomeCategoriesAdapter(getContext(),
                            categories,this);
            mBinding.recyclerViewCategoriesHomeFragment.
                    setAdapter(mHomeCategoriesAdapter);
        } else {
            mHomeCategoriesAdapter.setCategories(categories);
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