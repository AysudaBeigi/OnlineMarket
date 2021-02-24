package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.CardAdapter;
import com.example.onlinemarket.data.model.order.Order;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.databinding.FragmentShoppingBagBinding;
import com.example.onlinemarket.viewModel.ShoppingBagViewModel;

import java.util.List;


public class ShoppingBagFragment extends Fragment {

    public static String TAG = "OnlineMarket";
    private CardAdapter mCardAdapter;
    private FragmentShoppingBagBinding mBinding;
    private NavController mNavController;
    private ShoppingBagViewModel mShoppingBagViewModel;


    public ShoppingBagFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShoppingBagViewModel = new ViewModelProvider(this).
                get(ShoppingBagViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_shopping_bag, container,
                false);

        initViews();
        setupAdapters(mBinding.recyclerViewCards, mShoppingBagViewModel.getOrders());
        setListeners(this);
        return mBinding.getRoot();
    }

    private void setListeners(LifecycleOwner owner) {
        mBinding.buttonFinalizeShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShoppingBagViewModel.getCustomer() == null) {
                    replaceSignUpFragment();
                } else {
                    mShoppingBagViewModel.setPostOrderLiveData();
                    mShoppingBagViewModel.getPostOrderMutableLiveData()
                            .observe(owner, new Observer<Order>() {
                                @Override
                                public void onChanged(Order order) {
                                    //todo : going to pay the order
                                    Log.d(TAG, "postOrder+ onItemResponse + order is " +
                                            order.toString());

                                }
                            });

                }

            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = Navigation.findNavController(view);
    }

    private void replaceSignUpFragment() {
        mNavController.navigate(R.id.action_ShoppingBagFragment_to_SignUpFragment);
    }


    private void initViews() {
        if (mShoppingBagViewModel.isAnyProductInCard()) {
            setViewsVisibility();
            setObservers();
            mBinding.recyclerViewCards.setLayoutManager(new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL, false));

        }
        /*else {
            mBinding.recyclerViewCards.setVisibility(View.GONE);
            mBinding.buttonFinalizeShopping.setVisibility(View.GONE);
            mBinding.textViewSumPrices.setVisibility(View.VISIBLE);
            mBinding.textViewSum.setVisibility(View.VISIBLE);
            mBinding.imageViewEmptyShoppingBag.setVisibility(View.GONE);
            mBinding.textViewEmptyShoppingBag.setVisibility(View.GONE);

        }*/

    }

    private void setObservers() {
        mShoppingBagViewModel.getSumCardPrices()
                .observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        mBinding.textViewSumPrices.setText(integer);
                    }
                });
    }

    private void setViewsVisibility() {
        mBinding.recyclerViewCards.setVisibility(View.VISIBLE);
        mBinding.buttonFinalizeShopping.setVisibility(View.VISIBLE);
        mBinding.textViewSumPrices.setVisibility(View.VISIBLE);
        mBinding.textViewSum.setVisibility(View.VISIBLE);
        mBinding.imageViewEmptyShoppingBag.setVisibility(View.GONE);
        mBinding.textViewEmptyShoppingBag.setVisibility(View.GONE);

    }


    private void setupAdapters(RecyclerView recyclerView, List<Product> orderList) {

        if (mCardAdapter == null) {
            mCardAdapter = new CardAdapter(getContext(), orderList, this);
            recyclerView.setAdapter(mCardAdapter);
        } else {
            mCardAdapter.setProductList(orderList);
            mCardAdapter.notifyDataSetChanged();
        }

    }


}