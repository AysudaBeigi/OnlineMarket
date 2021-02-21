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
import com.example.onlinemarket.adapter.HomeFragmentCategoriesAdapter;
import com.example.onlinemarket.adapter.HomeProductsHorizontalAdapter;
import com.example.onlinemarket.adapter.ImageSliderAdapter;
import com.example.onlinemarket.data.model.product.Category;
import com.example.onlinemarket.data.model.product.Image;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.remote.NetworkParams;
import com.example.onlinemarket.data.repository.CategoryRepository;
import com.example.onlinemarket.databinding.FragmentHomeBinding;
import com.example.onlinemarket.viewModel.HomeViewModel;
import com.example.onlinemarket.viewModel.SearchResultViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

import java.util.List;


public class HomeFragment extends Fragment {

    private ImageSliderAdapter mImageSliderAdapter;
    private HomeProductsHorizontalAdapter mLastCategoryProductsHorizontalAdapter;
    private HomeProductsHorizontalAdapter mMostVisitedCategoryProductsHorizontalAdapter;
    private HomeProductsHorizontalAdapter mPopularCategoryProductsHorizontalAdapter;
    private HomeProductsHorizontalAdapter mAmazingOfferAdapter;
    private HomeFragmentCategoriesAdapter mHomeFragmentCategoriesAdapter;

    private HomeViewModel mProductViewModel;
    private CategoryRepository mCategoryRepository;
    private NavController mNavController;
    private FragmentHomeBinding mBinding;

    public static String TAG = "OnlineMarket";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductViewModel = new ViewModelProvider(this).
                get(HomeViewModel.class);
        setProductsLiveData();
        mCategoryRepository = new CategoryRepository(getActivity());
    }
    private void setProductsLiveData() {
        mProductViewModel.setSpecialProductLiveData();
        mProductViewModel.setAmazingOfferProductsLiveData();
        mProductViewModel.setLatestProductsLiveData();
        mProductViewModel.setMostVisitedProductsLiveData();
        mProductViewModel.setPopularProductsLivData();
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
        mProductViewModel.getSpecialProductLiveData().
                observe(this, new Observer<Product>() {
                    @Override
                    public void onChanged(Product product) {
                        setupImageSliderAdapter(product.getImages());

                    }
                });


        mProductViewModel.getLatestProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        setupRecyclerView(mBinding.recyclerViewLatest,
                                mLastCategoryProductsHorizontalAdapter, products);

                    }
                });

        mProductViewModel.getMostVisitedProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {

                        setupRecyclerView(mBinding.recyclerViewMostViewed
                                , mMostVisitedCategoryProductsHorizontalAdapter, products);

                    }
                });


        mProductViewModel.getPopularProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        setupRecyclerView(mBinding.recyclerViewPopularest,
                                mPopularCategoryProductsHorizontalAdapter, products);

                    }
                });

        mProductViewModel.getAmazingOfferProductsLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        setupRecyclerView(mBinding.recyclerViewWonderfulOffer
                                , mAmazingOfferAdapter, products);

                    }
                });
        mCategoryRepository.fetchCategories(
                new CategoryRepository.CategoriesCallback() {
                    @Override
                    public void onItemResponse(List<Category> categories) {
                        mBinding.recyclerViewCategoriesHomeFragment.
                                setLayoutManager(new LinearLayoutManager(getContext(),
                                        LinearLayoutManager.HORIZONTAL, false));
                        initCategoryAdapter(categories);
                    }
                });
    }


    private void setupRecyclerView(RecyclerView recyclerView,
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
        mNavController = Navigation.findNavController(view);
    }

    private void replaceSearchResultFragment(String query) {

        SearchResultViewModel searchResultViewModel=new ViewModelProvider(this)
                .get(SearchResultViewModel.class);
        searchResultViewModel.
                setSearchResultProductsLiveData(NetworkParams.getSearchAllProducts(query));
        mNavController.navigate(R.id.action_HomeFragment_to_SearchResultFragment);

    }


}