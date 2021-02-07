package com.example.onlinemarket.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.IOnBackPress;
import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.HomeFragmentCategoriesAdapter;
import com.example.onlinemarket.adapter.ProductsHorizontalAdapter;
import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.MarketRepository;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment implements IOnBackPress {
    public static final String TAG = "CategoryFragment";

    private RecyclerView mRecyclerViewCategoryOne, mRecyclerViewCategoryTwo,
            mRecyclerViewCategoryThree, mRecyclerViewCategoryFour,
            mRecyclerViewCategoryFive,
            mRecyclerViewCategorySix;

    private HomeFragmentCategoriesAdapter mAdapterOne, mAdapterTwo, mAdapterThree,
            mAdapterFour, mAdapterFive,
            mAdapterSix;
    private ProductsHorizontalAdapter mPAdapterOne, mPAdapterTwo, mPAdapterThree,
            mPAdapterFour, mPAdapterFive,
            mPAdapterSix;


    private ArrayList<HomeFragmentCategoriesAdapter> mHomeFragmentCategoriesAdapters = new ArrayList<>();
    private ArrayList<RecyclerView> mRecyclerViews = new ArrayList<>();
    private ArrayList<ProductsHorizontalAdapter> mProductsHorizontalAdapters = new ArrayList<>();
    private TextView mTextOne, mTextTwo, mTextThree, mTextFour, mTextFive, mTextSix;

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
        initCategoriesAdapters();
        initRecyclerViews();
        initProductHorizontalAdapters();

    }

    private void initProductHorizontalAdapters() {
        mProductsHorizontalAdapters.add(mPAdapterOne);
        mProductsHorizontalAdapters.add(mPAdapterTwo);
        mProductsHorizontalAdapters.add(mPAdapterThree);
        mProductsHorizontalAdapters.add(mPAdapterFour);
        mProductsHorizontalAdapters.add(mPAdapterFive);
        mProductsHorizontalAdapters.add(mPAdapterSix);
    }

    private void initRecyclerViews() {
        mRecyclerViews.add(mRecyclerViewCategoryOne);
        mRecyclerViews.add(mRecyclerViewCategoryTwo);
        mRecyclerViews.add(mRecyclerViewCategoryThree);
        mRecyclerViews.add(mRecyclerViewCategoryFour);
        mRecyclerViews.add(mRecyclerViewCategoryFive);
        mRecyclerViews.add(mRecyclerViewCategorySix);
    }

    private void initCategoriesAdapters() {
        mHomeFragmentCategoriesAdapters.add(mAdapterOne);
        mHomeFragmentCategoriesAdapters.add(mAdapterTwo);
        mHomeFragmentCategoriesAdapters.add(mAdapterThree);
        mHomeFragmentCategoriesAdapters.add(mAdapterFour);
        mHomeFragmentCategoriesAdapters.add(mAdapterFive);
        mHomeFragmentCategoriesAdapters.add(mAdapterSix);
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
        mRecyclerViewCategoryOne = view.findViewById(R.id.category_recycler_one);
        mRecyclerViewCategoryTwo = view.findViewById(R.id.category_recycler_two);
        mRecyclerViewCategoryThree = view.findViewById(R.id.category_recycler_three);
        mRecyclerViewCategoryFour = view.findViewById(R.id.category_recycler_four);
        mRecyclerViewCategoryFive = view.findViewById(R.id.category_recycler_five);
        mRecyclerViewCategorySix = view.findViewById(R.id.category_recycler_six);

        mTextOne = view.findViewById(R.id.category_name_one);
        mTextTwo = view.findViewById(R.id.category_name_two);
        mTextThree = view.findViewById(R.id.category_name_three);
        mTextFour = view.findViewById(R.id.category_name_four);
        mTextFive = view.findViewById(R.id.category_name_five);
        mTextSix = view.findViewById(R.id.category_name_six);

    }

    private void updateCategoriesRecyclerAdapter() {
        for (int i = 0; i < mHomeFragmentCategoriesAdapters.size(); i++) {
            HomeFragmentCategoriesAdapter categoryAdapter = mHomeFragmentCategoriesAdapters.get(i);
            ProductsHorizontalAdapter productsHorizontalAdapter = mProductsHorizontalAdapters.get(i);
            RecyclerView recyclerView = mRecyclerViews.get(i);
            int categoryId = mCategories.get(i).getId();
            mMarketRepository.fetchSubCategories(mCategories.get(i).getId(),
                    new MarketRepository.subCategoriesCallback() {
                        @Override
                        public void onItemResponse(List<Category> subCategories) {
                            if (subCategories.size() > 0) {
                                initCategoriesAdapter(subCategories, categoryAdapter, recyclerView);

                            } else {
                                fetchProductsAndInitProductHorizontalAdapter(categoryId, productsHorizontalAdapter, recyclerView);
                            }
                        }

                    });

        }

    }

    private void initCategoriesAdapter(List<Category> subCategories,
                                       HomeFragmentCategoriesAdapter adapter, RecyclerView recyclerView) {

        if (adapter == null) {
            adapter = new HomeFragmentCategoriesAdapter(getContext(),
                    subCategories);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setCategoriesItem(subCategories);
            adapter.notifyDataSetChanged();
        }
    }

    private void fetchProductsAndInitProductHorizontalAdapter(int categoryId,
                                                              ProductsHorizontalAdapter adapter,
                                                              RecyclerView recyclerView) {

        mMarketRepository.fetchCategoryProduct(categoryId,
                new MarketRepository.productsCallback() {
                    @Override
                    public void onItemResponse(List<Product> products) {

                        initProductHorizontalAdapter(adapter, recyclerView,products);
                    }
                });
    }

    private void initProductHorizontalAdapter(ProductsHorizontalAdapter adapter,
                                              RecyclerView recyclerView, List<Product> products) {
        if (adapter == null) {
            adapter = new ProductsHorizontalAdapter(getContext(),
                    products);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setProductsItem(products);
            adapter.notifyDataSetChanged();
        }
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
