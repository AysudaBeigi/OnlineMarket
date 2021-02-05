 package com.example.onlinemarket.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.onlinemarket.IOnBackPress;
import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.CategoriesAdapter;
import com.example.onlinemarket.adapter.ProductsVerticalAdapter;
import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.repository.MarketRepository;

import java.util.ArrayList;
import java.util.List;

 public class CategoriesFragment extends Fragment  implements IOnBackPress {
         public static final String TAG = "CategoryFragment";

         private RecyclerView mRecyclerViewCategoryOne, mRecyclerViewCategoryTwo,
                 mRecyclerViewCategoryThree, mRecyclerViewCategoryFour,
                 mRecyclerViewCategoryFive,
                 mRecyclerViewCategorySix;

         private CategoriesAdapter mAdapterOne, mAdapterTwo, mAdapterThree,
                 mAdapterFour, mAdapterFive,
                 mAdapterSix;

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

             mMarketRepository.fetchCategories(1,
                     new MarketRepository.CategoriesCallback() {
                 @Override
                 public void onItemResponse(List<Category> categories) {
                     mCategories= categories;
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
             mMarketRepository.fetchSubCategories(mCategories.get(0).getId(),
                     new MarketRepository.subCategoriesCallback() {
                 @Override
                 public void onItemResponse(List<Category> subCategories) {
                     if (mAdapterOne == null) {
                         mAdapterOne = new CategoriesAdapter(getContext(), subCategories);
                         mRecyclerViewCategoryOne.setAdapter(mAdapterOne);
                     } else {
                         mAdapterOne.setCategoriesItem(subCategories);
                         mAdapterOne.notifyDataSetChanged();
                     }
                 }
             });

             mMarketRepository.fetchSubCategories(mCategories.get(1).getId(),
                     new MarketRepository.subCategoriesCallback() {
                         @Override
                         public void onItemResponse(List<Category> subCategories) {
                             if (mAdapterTwo == null) {
                                 mAdapterTwo = new CategoriesAdapter(getContext(),
                                         subCategories);
                                 mRecyclerViewCategoryOne.setAdapter(mAdapterTwo);
                             } else {
                                 mAdapterTwo.setCategoriesItem(subCategories);
                                 mAdapterTwo.notifyDataSetChanged();
                             }
                         }
                     });

             mMarketRepository.fetchSubCategories(mCategories.get(2).getId(),
                     new MarketRepository.subCategoriesCallback() {
                         @Override
                         public void onItemResponse(List<Category> subCategories) {
                             if (mAdapterThree == null) {
                                 mAdapterThree = new CategoriesAdapter(getContext(),
                                         subCategories);
                                 mRecyclerViewCategoryOne.setAdapter(mAdapterThree);
                             } else {
                                 mAdapterThree.setCategoriesItem(subCategories);
                                 mAdapterThree.notifyDataSetChanged();
                             }
                         }
                     });

             mMarketRepository.fetchSubCategories(mCategories.get(3).getId(),
                     new MarketRepository.subCategoriesCallback() {
                         @Override
                         public void onItemResponse(List<Category> subCategories) {
                             if (mAdapterFour == null) {
                                 mAdapterFour = new CategoriesAdapter(getContext(),
                                         subCategories);
                                 mRecyclerViewCategoryOne.setAdapter(mAdapterFour);
                             } else {
                                 mAdapterFour.setCategoriesItem(subCategories);
                                 mAdapterFour.notifyDataSetChanged();
                             }
                         }
                     });
             mMarketRepository.fetchSubCategories(mCategories.get(4).getId(),
                     new MarketRepository.subCategoriesCallback() {
                         @Override
                         public void onItemResponse(List<Category> subCategories) {
                             if (mAdapterFive == null) {
                                 mAdapterFive = new CategoriesAdapter(getContext(),
                                         subCategories);
                                 mRecyclerViewCategoryOne.setAdapter(mAdapterFive);
                             } else {
                                 mAdapterFive.setCategoriesItem(subCategories);
                                 mAdapterFive.notifyDataSetChanged();
                             }
                         }
                     });
             mMarketRepository.fetchSubCategories(mCategories.get(5).getId(),
                     new MarketRepository.subCategoriesCallback() {
                         @Override
                         public void onItemResponse(List<Category> subCategories) {
                             if (mAdapterSix== null) {
                                 mAdapterSix = new CategoriesAdapter(getContext(),
                                         subCategories);
                                 mRecyclerViewCategoryOne.setAdapter(mAdapterSix);
                             } else {
                                 mAdapterSix.setCategoriesItem(subCategories);
                                 mAdapterSix.notifyDataSetChanged();
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
}